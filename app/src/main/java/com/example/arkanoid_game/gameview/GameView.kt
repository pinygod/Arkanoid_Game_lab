package com.example.arkanoid_game.gameview

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.example.arkanoid_game.R
import com.example.arkanoid_game.objects.Ball
import com.example.arkanoid_game.objects.Enemy
import com.example.arkanoid_game.objects.PlayerPlatform
import kotlinx.android.synthetic.main.activity_game.view.*
import kotlinx.coroutines.*
import kotlin.math.abs

class GameView(context: Context) : SurfaceView(context),
    SurfaceHolder.Callback {

    companion object{
        const val DISTANCE_BETWEEN_UPDATES = 15
    }

    private val platform =
        PlayerPlatform(context, context.getDrawable(R.drawable.paddle)!!.toBitmap())
    private val ball = Ball(context.getDrawable(R.drawable.ball)!!.toBitmap())
    private val enemies = ArrayList<Enemy>()
    private val countOfEnemies: Int
    private var gameThread: DrawThread?
    var gameThread2: Thread? = null


    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    var score = 0
    var pause = false
    var ended = false
    private var gameRunning = false
    private var isIncreased = false

    private val enemy = context.getDrawable(R.drawable.element_green_rectangle)!!.toBitmap()
    private var enemyJob: Job = Job()

    init {

        holder.addCallback(this)

        gameThread = DrawThread(holder, this)
        setZOrderOnTop(false)
        holder.setFormat(PixelFormat.TRANSPARENT)

        val enemyWidth: Int = enemy.width
        val enemyHeight: Int = enemy.height
        countOfEnemies = screenWidth / (enemyWidth + Enemy.MARGIN)

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        gameThread?.stop()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)


        if (canvas != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

            platform.draw(canvas)
            ball.draw(canvas)
            enemies.forEach {
                it.draw(canvas)
            }
        } else return

    }


    fun update() {
        //  val new_score = score

        platform.update()
        ball.update()

        if (ball.getBottom() >= platform.getBottom()) {
            gameThread?.stop()
            endGame()
        }

        if (ball.getBottom() >= platform.getTop()) {
            if (ball.getRight() >= platform.getLeft() && ball.getLeft() <= platform.getRight()) {
                ball.verticalReverse()
            }
        }

        for (enemy in enemies) {
            enemy.update()
            if (enemy.getBottom() >= screenHeight) {
                gameThread?.stop()
                endGame()
            } else if (ball.getRight() >= enemy.getLeft() && ball.getLeft() <= enemy.getRight() && ball.getBottom() >= enemy.getTop() && ball.getTop() <= enemy.getBottom()) {
                enemy.hit()
                if (ball.getBottom() >= enemy.getTop() && ball.getTop() <= enemy.getBottom() && !(ball.getRight() >= enemy.getLeft() + DISTANCE_BETWEEN_UPDATES && ball.getLeft() <= enemy.getRight() - DISTANCE_BETWEEN_UPDATES)) ball.horizontalReverse()
                else ball.verticalReverse()
                if (enemy.hp == 0) {
                    increaseScore(enemy.getCost())
                    enemies.remove(enemy)
                    break
                }
            }
        }
    }

    fun createEnemies(): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                if (!this.isActive) break

                delay(5000L)
                for (i in 0 until countOfEnemies) {
                    val enemy: Enemy = if (i == 0) {
                        val count = screenWidth / (enemy.width + Enemy.MARGIN)
                        val margins = Enemy.MARGIN * (count - 1)
                        val startX = (screenWidth - (count * enemy.width) - margins) / 2
                        Enemy(enemy, 1, 1, startX) // mb random hp and cost
                    } else
                        Enemy(enemy, 1, 1, enemies.last().getRight() + Enemy.MARGIN)

                    enemies.add(enemy)
                }
            }

            return@launch
        }
    }


    private fun increaseScore(points: Int) {
        score += points
    }

    fun pauseGame() {
        pause = true
    }

    fun resumeGame() {
        pause = false
    }

    private fun endGame() {
        ended = true
    }

    fun startGame() {

        gameThread = DrawThread(holder, this)
        gameThread?.start()
        enemyJob = createEnemies()
        score = 0
        pause = false
        ended = false
        //gameThread.run { start() }
        // gameThread2.run { createEnemies() }

        //создать блоки
    }

    fun stopGame() {

        gameThread?.stop()
        gameThread = null
        GlobalScope.launch(Dispatchers.Main) {
            enemyJob.cancelAndJoin()
        }

    }
}
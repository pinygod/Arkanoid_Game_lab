package com.example.arkanoid_game.gameview

import android.app.Activity
import android.content.res.Resources
import android.graphics.*
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.arkanoid_game.R
import com.example.arkanoid_game.objects.Ball
import com.example.arkanoid_game.objects.Enemy
import com.example.arkanoid_game.objects.PlayerPlatform
import com.example.arkanoid_game.ui.game.GameViewModel
import kotlinx.android.synthetic.main.activity_game.view.*
import kotlinx.coroutines.*

class GameView(private val activity: Activity, private val viewModel: GameViewModel) :
    SurfaceView(activity),
    SurfaceHolder.Callback {

    companion object {
        const val DISTANCE_BETWEEN_UPDATES = 15
    }

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    private var gameThread: DrawThread? = null

    private var elapsedTime: Long = 0
    private var now: Long = 0

    private val platform =
        PlayerPlatform(context, ContextCompat.getDrawable(activity, R.drawable.paddle)!!.toBitmap())
    private var ball = Ball(ContextCompat.getDrawable(activity, R.drawable.m2)!!.toBitmap())
    private val enemy = ContextCompat.getDrawable(activity, R.drawable.enemy2)!!.toBitmap()

    private val enemies = ArrayList<Enemy>()
    private val countOfEnemies: Int

    private var enemyJob: Job = Job()

    init {

        holder.addCallback(this)
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        countOfEnemies = screenWidth / (enemy.width + Enemy.MARGIN)
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
        } else
            return
    }

    fun update() {
        platform.update()
        ball.update()

        if (ball.getBottom() >= platform.getBottom()) {
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
                endGame()
            } else if (ball.getRight() >= enemy.getLeft() && ball.getLeft() <= enemy.getRight() && ball.getBottom() >= enemy.getTop() && ball.getTop() <= enemy.getBottom()) {
                enemy.hit()
                if (ball.getBottom() >= enemy.getTop() && ball.getTop() <= enemy.getBottom() && !(ball.getRight() >= enemy.getLeft() + DISTANCE_BETWEEN_UPDATES && ball.getLeft() <= enemy.getRight() - DISTANCE_BETWEEN_UPDATES)) ball.horizontalReverse()
                else ball.verticalReverse()
                if (enemy.hp == 0) {
                    viewModel.increaseScore(enemy.getCost())
                    enemies.remove(enemy)
                    break
                }
            }
        }
    }

    private fun createEnemies(): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                if (!this.isActive) break

                if (!viewModel.isPaused.value!!) {
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
                    now = System.currentTimeMillis()
                    delay(10000L)
                }
            }

            return@launch
        }
    }

    fun pauseGame() {
        elapsedTime = (System.currentTimeMillis() - now)
        viewModel.pauseGame()
        enemyJob.cancel()
        gameThread?.stop()
    }

    fun resumeGame() {
        viewModel.resumeGame()
        gameThread?.start()
        CoroutineScope(Dispatchers.Main).launch {
            delay(10000L - elapsedTime)
            enemyJob = createEnemies()
        }
    }

    fun startGame() {
        viewModel.startGame()
        gameThread = DrawThread(holder, this)
        gameThread?.start()
        enemyJob = createEnemies()
    }

    fun endGame() {
        gameThread?.stop()
        gameThread = null
        GlobalScope.launch(Dispatchers.Main) {
            enemyJob.cancelAndJoin()
        }
        viewModel.endGame()
    }
}
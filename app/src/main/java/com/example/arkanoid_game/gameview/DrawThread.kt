package com.example.arkanoid_game.gameview

import android.graphics.Canvas
import android.view.SurfaceHolder
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class DrawThread(private val holder: SurfaceHolder, private val gameView: GameView)  : CoroutineScope {
    companion object {
        private var canvas: Canvas? = null
    }
    private var runFlag = false
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    private fun run() {
        launch {

            while (runFlag) {
                val now = System.currentTimeMillis()
                canvas = null
                try {
                    canvas = holder.lockCanvas()
                    // получаем объект Canvas и выполняем отрисовку
                    synchronized(holder) {
                        gameView.update()
                        gameView.draw(canvas)
                    }
                }
                finally {
                    if (canvas != null) {
                            // отрисовка выполнена. выводим результат на экран
                            holder.unlockCanvasAndPost(canvas)
                    }
                }

                val elapsedTime = (System.currentTimeMillis() - now)
                val delayTime = 1000L/30 - elapsedTime

                delay(delayTime)
            }

            return@launch
        }
    }
    fun start() {
        runFlag = true
        run()
    }
    fun stop() {
        runFlag = false
    }
}
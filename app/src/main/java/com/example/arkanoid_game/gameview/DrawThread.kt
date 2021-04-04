package com.example.arkanoid_game.gameview

import android.graphics.Canvas
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class DrawThread(private val gameView: GameView)  : CoroutineScope {

    private var runFlag = false
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private fun run() {
        launch {
            while (runFlag) {
                val now = System.currentTimeMillis()
                gameView.update()
                gameView.draw(gameView.canvas)

                val elapsedTime = (System.currentTimeMillis() - now)
                val delayTime = 1000L/60 - elapsedTime

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
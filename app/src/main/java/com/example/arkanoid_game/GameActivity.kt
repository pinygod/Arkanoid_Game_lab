package com.example.arkanoid_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.arkanoid_game.gameview.GameView
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameView = GameView(this)
        gameView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        kek.addView(gameView)

    }

    override fun onResume() {
        super.onResume()
        gameView.startGame()
        gameView.resumeGame()
    }

    override fun onPause() {
        super.onPause()
        gameView.stopGame()
        gameView.pauseGame()
    }
}
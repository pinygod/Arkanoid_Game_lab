package com.example.arkanoid_game.ui.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.arkanoid_game.AppHelper
import com.example.arkanoid_game.R
import com.example.arkanoid_game.databinding.ActivityGameBinding
import com.example.arkanoid_game.gameview.GameView
import com.example.arkanoid_game.ui.maps.MapsViewModel
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private val gameView : GameView by lazy { GameView(this) }
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        binding.apply {
            lifecycleOwner = this@GameActivity
            gameEnded = gameView.ended
            gamePaused = gameView.pause
            gameRunning = gameView.gameRunning
            gameScore = gameView.score
            pauseButton.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.pauseGame()
            }
            resumeButton.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.resumeGame()
            }
            exitButton.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                finish()
            }
            exitButtonLost.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                finish()
            }
            restartButton.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                startActivity(Intent(this@GameActivity, GameActivity::class.java))
                finish()
            }
            lvl1.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.startGame()
                //add lvl definition
            }
            lvl2.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.startGame()
                //add lvl definition
            }
            lvl3.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.startGame()
                //add lvl definition
            }

        }

        gameView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.surfaceHolder.addView(gameView)
    }

    override fun onResume() {
        super.onResume()
        //gameView.startGame()
        //gameView.resumeGame()
    }

    override fun onPause() {
        super.onPause()
        gameView.stopGame()
        gameView.pauseGame()
    }
}
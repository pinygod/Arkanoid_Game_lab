package com.example.arkanoid_game.ui.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.example.arkanoid_game.AppHelper
import com.example.arkanoid_game.R
import com.example.arkanoid_game.databinding.ActivityGameBinding
import com.example.arkanoid_game.gameview.GameView
import com.example.arkanoid_game.ui.maps.MapsViewModel
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    private val viewModel : GameViewModel by lazy { GameViewModel(this) }
    private val gameView : GameView by lazy { GameView(this, viewModel) }
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        binding.apply {
            lifecycleOwner = this@GameActivity
            gameEnded = viewModel.isEnded
            gamePaused = viewModel.isPaused
            gameRunning = viewModel.isGameRunning
            gameScore = viewModel.score
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

    override fun onPause() {
        gameView.pauseGame()
        super.onPause()
    }

    override fun onDestroy() {
        gameView.endGame()
        super.onDestroy()
    }
}
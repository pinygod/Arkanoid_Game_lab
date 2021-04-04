package com.example.arkanoid_game.ui.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.arkanoid_game.AppHelper
import com.example.arkanoid_game.R
import com.example.arkanoid_game.databinding.ActivityGameBinding
import com.example.arkanoid_game.gameview.GameView

class GameActivity : AppCompatActivity() {
    private val viewModel by lazy { GameViewModel(this) }
    private val gameView by lazy { GameView(this, viewModel) }
    private lateinit var binding: ActivityGameBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        includeMusic()

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
                mediaPlayer.stop()
                AppHelper.playClickSound(this@GameActivity)
                finish()
            }
            exitButtonLost.setOnClickListener {
                mediaPlayer.stop()
                AppHelper.playClickSound(this@GameActivity)
                finish()
            }
            restartButton.setOnClickListener {
                mediaPlayer.stop()
                AppHelper.playClickSound(this@GameActivity)
                startActivity(Intent(this@GameActivity, GameActivity::class.java))
                finish()
            }
            lvl1.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.startGame()
                gameView.setFirstLvl()
            }
            lvl2.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.startGame()
                gameView.setSecondLvl()
            }
            lvl3.setOnClickListener {
                AppHelper.playClickSound(this@GameActivity)
                gameView.startGame()
                gameView.setThirdLvl()
            }
        }

        gameView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.elementsHolder.addView(gameView, 0)
    }

    private fun includeMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.grasslandstheme)
        mediaPlayer.isLooping = true
    }

    override fun onPause() {
        gameView.pauseGame()
        mediaPlayer.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onDestroy() {
        gameView.endGame()
        super.onDestroy()
    }
}
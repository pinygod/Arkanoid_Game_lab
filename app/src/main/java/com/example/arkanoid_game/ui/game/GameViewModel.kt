package com.example.arkanoid_game.ui.game

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arkanoid_game.RankingRepository

class GameViewModel(private val activity: Activity) : ViewModel() {
    var score = MutableLiveData(0)
    var isPaused = MutableLiveData(false)
    var isEnded = MutableLiveData(false)
    var isGameRunning = MutableLiveData(false)

    fun saveScore() {
        RankingRepository(activity).pushScore(score.value!!)
    }

    fun pauseGame() {
        isPaused.value = true
    }

    fun resumeGame() {
        isPaused.value = false
    }

    fun endGame() {
        pauseGame()
        isEnded.value = true
    }

    fun startGame() {
        reset()
        isGameRunning.value = true
    }

    fun reset(){
        score.value = 0
        isPaused.value = false
        isEnded.value = false
        isGameRunning.value = false
    }

    fun increaseScore(score : Int){
        this.score.value = this.score.value?.plus(score)
    }
}
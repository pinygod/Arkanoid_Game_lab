package com.example.arkanoid_game.ui.ranking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arkanoid_game.RankingRepository
import com.example.arkanoid_game.data.RankingItem

class RankingViewModel(activity: RankingActivity) : ViewModel() {

    var playersResults = MutableLiveData<ArrayList<RankingItem>>()

    init {
        playersResults = RankingRepository(activity).getRatingList()
    }
}
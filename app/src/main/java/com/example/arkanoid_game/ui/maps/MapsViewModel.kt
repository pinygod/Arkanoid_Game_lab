package com.example.arkanoid_game.ui.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arkanoid_game.RankingRepository
import com.example.arkanoid_game.data.RankingItem

class MapsViewModel(activity: MapsActivity) : ViewModel() {

    var playersResults: MutableLiveData<ArrayList<RankingItem>> = MutableLiveData()

    init {
        playersResults = RankingRepository(activity).getRatingList()
    }
}
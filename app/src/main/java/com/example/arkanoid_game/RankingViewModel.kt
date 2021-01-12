package com.example.arkanoid_game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class RankingViewModel() : ViewModel() {

    fun recyclerIsReady(callback: (adapter: RankingRecyclerAdapter) -> Unit) {
        RankingRepository().getRatingList(callback)
    }


}
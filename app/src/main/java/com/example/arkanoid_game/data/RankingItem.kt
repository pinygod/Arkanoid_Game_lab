package com.example.arkanoid_game.data

import com.example.arkanoid_game.data.SessionResult

data class RankingItem(
    var name: String = "",
    val result: SessionResult? = null
)
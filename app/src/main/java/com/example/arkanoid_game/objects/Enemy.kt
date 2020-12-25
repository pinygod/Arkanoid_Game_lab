package com.example.arkanoid_game.objects

import android.graphics.Bitmap

class Enemy(bitmap: Bitmap, private val cost: Int, var hp: Int, x: Int = 10, y: Int = 120) :
    GameObject(bitmap) {
    companion object {
        const val MARGIN = 15
        var verticalVelocity = 0
    }

    init {
        this.x = x
        this.y = y
        reset()
    }

    fun hit(): Int {
        hp--
        return if (hp <= 0)
            cost
        else
            0
        //later big enemies will return partition hit
    }

    override fun update() {
        move(0, verticalVelocity)
    }

    override fun increaseVelocity() {
        if (verticalVelocity < 12) verticalVelocity++
    }

    override fun reset() {
        verticalVelocity = 1
    }
}
package com.example.arkanoid_game.objects

import android.graphics.Bitmap

class Enemy(bitmap: Bitmap, private val cost: Int, var hp: Int, x: Int = 10, y: Int = 110) :
    GameObject(bitmap) {
    companion object {
        const val MARGIN = 20
        var verticalVelocity = 0
    }

    init {
        this.x = x
        this.y = y
        reset()
    }

    fun hit() {
        hp--
      /*  return if (hp <= 0)
            cost
        else
            0*/
        //later big enemies will return partition hit
    }

    fun getCost() : Int{
        return cost
    }

    override fun update() {
        move(0, verticalVelocity)
    }



    override fun reset() {
        verticalVelocity = 1
    }
}
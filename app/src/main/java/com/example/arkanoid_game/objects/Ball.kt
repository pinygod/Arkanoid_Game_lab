package com.example.arkanoid_game.objects

import android.graphics.Bitmap

class Ball(bitmap: Bitmap) : GameObject(bitmap) {

    init {
        reset()
    }

    private var horizontalVelocity = 11
    private var verticalVelocity = -11

    override fun update() {
        move(horizontalVelocity, verticalVelocity)
        if (this.getTop() <= 0 || this.getBottom() >= screenHeight) verticalReverse()
        if (this.getLeft() <= 0 || this.getRight() >= screenWidth) horizontalReverse()
    }


    override fun reset() {
        x = screenWidth / 2
        y = screenHeight / 2
    }

    fun horizontalReverse() {
        horizontalVelocity = -horizontalVelocity
    }

    fun verticalReverse() {
        verticalVelocity = -verticalVelocity
    }
}
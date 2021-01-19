package com.example.arkanoid_game.objects

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas

abstract class GameObject(private val image: Bitmap) {
    private val width: Int = image.width
    private val height: Int = image.height
    protected var x = 0
    protected var y = 0
    protected val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    protected val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    fun getLeft(): Int {
        return x
    }

    fun getRight(): Int {
        return x + width
    }

    fun getBottom(): Int {
        return y + height
    }

    fun getTop(): Int {
        return y
    }

    fun getCenterX(): Int {
        return x + width / 2
    }

    fun getCenterY(): Int {
        return y + height / 2
    }

    fun move(horLen: Int, verLen: Int) {
        x = (screenWidth - width).coerceAtMost(0.coerceAtLeast(x + horLen))
        y = (screenHeight - height).coerceAtMost(0.coerceAtLeast(y + verLen))
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    abstract fun update()

    abstract fun reset()
}
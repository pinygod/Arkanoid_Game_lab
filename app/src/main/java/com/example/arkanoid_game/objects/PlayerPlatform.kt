package com.example.arkanoid_game.objects

import android.content.Context
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class PlayerPlatform(context: Context, bitmap: Bitmap) : GameObject(bitmap),
    SensorEventListener {
    companion object {
        private const val MARGIN = 15
        private const val DEFAULT_VELOCITY = 15
    }

    private var horizontalVelocity = 0

    init {
        reset()

        val manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val velocity = event.values.first()

            horizontalVelocity += -velocity.toInt()

            horizontalVelocity = if (horizontalVelocity <= 0)
                horizontalVelocity.coerceAtLeast(-(this.getLeft()))
            else
                horizontalVelocity.coerceAtMost(screenWidth - this.getLeft())


            if (horizontalVelocity > DEFAULT_VELOCITY) horizontalVelocity = DEFAULT_VELOCITY
            if (horizontalVelocity < -DEFAULT_VELOCITY) horizontalVelocity = -DEFAULT_VELOCITY
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //nothing interesting for us
    }

    override fun update() {
        move(horizontalVelocity, 0)
    }


    override fun reset() {
        x = screenWidth / 2
        y = screenHeight - MARGIN - getBottom() + getTop()
    }
}
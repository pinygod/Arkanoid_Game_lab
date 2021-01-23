package com.example.arkanoid_game

import android.app.Activity
import android.content.pm.PackageManager

class Permissions(private val activity: Activity) {

    fun check(permission: String): Boolean {
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun request(permissions: Array<String>, requestCode: Int) {
        activity.requestPermissions(permissions, requestCode)
    }
}
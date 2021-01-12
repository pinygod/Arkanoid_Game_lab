package com.example.arkanoid_game

import android.app.Activity
import android.content.Context

class MenuModel {
    fun getUsername(activity: Activity): String? {
        return activity.getPreferences(Context.MODE_PRIVATE)
            .getString(activity.getString(R.string.username_key), null)
    }

    fun saveUsername(activity: Activity, username: String){
        activity.getPreferences(Context.MODE_PRIVATE).edit().putString(activity.getString(R.string.username_key), username).apply()
    }
}
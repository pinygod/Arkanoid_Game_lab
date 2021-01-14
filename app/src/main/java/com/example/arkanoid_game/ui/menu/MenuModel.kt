package com.example.arkanoid_game.ui.menu

import android.app.Activity
import android.content.Context
import com.example.arkanoid_game.Constants

class MenuModel {
    fun getUsername(activity: Activity): String? {
        return activity.getSharedPreferences(Constants.APP_PREF_KEY, Context.MODE_PRIVATE)
            .getString(Constants.USERNAME_KEY, null)
    }

    fun saveUsername(activity: Activity, username: String){
        activity.getSharedPreferences(Constants.APP_PREF_KEY, Context.MODE_PRIVATE).edit().putString(
            Constants.USERNAME_KEY, username).apply()
    }
}
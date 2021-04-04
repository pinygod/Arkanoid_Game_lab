package com.example.arkanoid_game.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    private var model: MenuModel? =
        MenuModel()
    var username: MutableLiveData<String> = MutableLiveData()

    fun onResume(activity: MenuActivity) {
        username.value = model?.getUsername(activity)
    }

    fun changeUsername(activity: MenuActivity, username: String) {
        model?.saveUsername(activity, username)
        this.username.value = username
    }

    fun onDestroy() {
        model = null
    }
}
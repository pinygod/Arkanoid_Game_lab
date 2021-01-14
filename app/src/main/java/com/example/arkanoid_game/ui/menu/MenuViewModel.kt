package com.example.arkanoid_game.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arkanoid_game.ui.menu.MenuActivity
import com.example.arkanoid_game.ui.menu.MenuModel

class MenuViewModel : ViewModel() {

    var model : MenuModel? =
        MenuModel()
    var username: MutableLiveData<String> = MutableLiveData()

    fun onResume(activity: MenuActivity) {
        username.value = model?.getUsername(activity)
    }

    fun changeUsername(activity: MenuActivity, username: String) {
        model?.saveUsername(activity, username)
    }

    fun onDestroy(){
        model = null
    }
}
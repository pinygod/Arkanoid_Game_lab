package com.example.arkanoid_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppHelper {
    companion object{
        inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
            }
    }
}
package com.example.arkanoid_game.ui.menu

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.arkanoid_game.*
import com.example.arkanoid_game.databinding.ActivityMenuBinding
import com.example.arkanoid_game.ui.game.GameActivity
import com.example.arkanoid_game.ui.maps.MapsActivity
import com.example.arkanoid_game.ui.ranking.RankingActivity

class MenuActivity : AppCompatActivity() {

    private val viewModel by viewModels<MenuViewModel>()
    private lateinit var binding : ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_menu
        )
        binding.username = viewModel.username
        binding.userNickname.addTextChangedListener(afterTextChanged = {viewModel.changeUsername(this, binding.userNickname.text.toString())})

        binding.rankingButton.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }
        binding.playButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
        binding.mapButton.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        requestNecessaryPermissions()
        startNotificationsService()
    }

    private fun requestNecessaryPermissions() {
        Permissions(this)
            .request(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(this)
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    fun startNotificationsService(){
        val startIntent = Intent(applicationContext, NotificationsService::class.java)
        startIntent.action = "START"
        startService(startIntent)
    }
}
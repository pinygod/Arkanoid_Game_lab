package com.example.arkanoid_game.ui.menu

import android.Manifest
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
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
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_menu
        )
        binding.apply {
            title.startAnimation(AnimationUtils.loadAnimation(this@MenuActivity, R.anim.title_anim))
            username = viewModel.username
            userNickname.addTextChangedListener(afterTextChanged = {
                viewModel.changeUsername(
                    this@MenuActivity,
                    binding.userNickname.text.toString()
                )
            })

            playButton.setOnClickListener {
                AppHelper.playClickSound(this@MenuActivity)
                if (viewModel.username.value != null && Permissions(this@MenuActivity).check(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
                    startActivity(Intent(this@MenuActivity, GameActivity::class.java))
                else {
                    Toast.makeText(
                        this@MenuActivity,
                        "We need this permission to save your result.",
                        Toast.LENGTH_LONG
                    ).show()
                    requestNecessaryPermissions()
                }
            }

            rankingButton.setOnClickListener {
                AppHelper.playClickSound(this@MenuActivity)
                startActivity(Intent(this@MenuActivity, RankingActivity::class.java))
            }

            mapButton.setOnClickListener {
                AppHelper.playClickSound(this@MenuActivity)
                startActivity(Intent(this@MenuActivity, MapsActivity::class.java))
            }
        }

        requestNecessaryPermissions()
        startNotificationsService()
    }

    private fun requestNecessaryPermissions() {
        Permissions(this)
            .request(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1
            )
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(this)
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    private fun startNotificationsService() {
        val startIntent = Intent(applicationContext, NotificationsService::class.java)
        startIntent.action = "START"
        startService(startIntent)
    }
}
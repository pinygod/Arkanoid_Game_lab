package com.example.arkanoid_game

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import com.example.arkanoid_game.ui.menu.MenuActivity
import kotlinx.coroutines.*


class NotificationsService : Service() {

    private var wakeLock: PowerManager.WakeLock? = null
    private var isServiceStarted = false
    private lateinit var notificationManager: NotificationManager

    private val notificationCycle = Runnable {
        kotlin.run {
            while (isServiceStarted) {
                Thread.sleep(10 * 60 * 1000L)
                sendNotification()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        startService()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent != null && intent.getAction().equals("START")) {
            startService()
        } else stopService()
        return START_STICKY
    }

    private fun startService() {
        if (isServiceStarted) return
        isServiceStarted = true
        wakeLock =
            (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
                newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ArkanoidNotificationsService::lock").apply {
                    acquire()
                }
            }

        Thread(notificationCycle).start()
    }

    private fun stopService() {
        try {
            wakeLock?.let {
                if (it.isHeld)
                    it.release()
            }
            stopForeground(true)
            stopSelf()
            isServiceStarted = false
        } catch (e: Exception) {
            //I don't even know what to do in this case haha
        }
    }

    private fun sendNotification() {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, getString(R.string.channel_name))
        } else {
            Notification.Builder(this)
        }

        val pIntent = PendingIntent.getActivity(this, 0, Intent(this, MenuActivity::class.java), 0)

        notification.setContentTitle("Miss you :(")
        notification.setContentText("Tap me and play the best game in the world!")
        notification.setSmallIcon(R.mipmap.ic_launcher)
        notification.setAutoCancel(true)
        notification.setContentIntent(pIntent)

        notificationManager.notify(1, notification.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(getString(R.string.channel_name), name, importance).apply {
                    description = descriptionText
                }
            notificationManager.createNotificationChannel(channel)
        }
    }


    override fun onBind(arg0: Intent): IBinder? {
        return null
    }
}

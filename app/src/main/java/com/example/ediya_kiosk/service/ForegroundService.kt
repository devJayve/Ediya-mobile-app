package com.example.ediya_kiosk.service

import android.app.*
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.session.PlaybackState.ACTION_STOP
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.ediya_kiosk.BuildConfig
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.StartActivity
import com.example.ediya_kiosk_Logic.Main

class ForegroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action != null && intent.action!!.equals(
                ACTION_STOP, ignoreCase = true)) {
                    stopForeground(true)
            stopSelf()
        }
        generateForegroundNotification()
        return START_NOT_STICKY
    }

    private var iconNotification: Bitmap? = null
    private var notification: Notification? = null
    var mNotificationManager: NotificationManager? = null
    private val mNotificationId = 123

    private fun generateForegroundNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, MainActivity::class.java)
            intentMainLanding.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intentMainLanding, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            iconNotification = BitmapFactory.decodeResource(resources, R.mipmap.ic_main_round)
            if (mNotificationManager == null) {
                mNotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                assert(mNotificationManager != null)
                mNotificationManager?.createNotificationChannelGroup(
                    NotificationChannelGroup("chats_group", "Chats")
                )
                val notificationChannel =
                    NotificationChannel("service_channel", "Service Notifications",
                        NotificationManager.IMPORTANCE_MIN)
                notificationChannel.enableLights(false)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_SECRET
                mNotificationManager?.createNotificationChannel(notificationChannel)
            }
            val builder = NotificationCompat.Builder(this, "service_channel")

            builder.setContentTitle(StringBuilder("장바구니 정보"))
                .setTicker(StringBuilder("장바구니 정보"))
                .setContentText("Touch to open") //                    , swipe down for more options.
                //.setSmallIcon(R.drawable.ediya_icon)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setWhen(0)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
            if (iconNotification != null) {
                builder.setLargeIcon(Bitmap.createScaledBitmap(iconNotification!!, 128, 128, false))
            }
            builder.color = resources.getColor(R.color.white)
            notification = builder.build()
            startForeground(mNotificationId, notification)
        }
    }

    fun getBasketData() {
    }

    companion object{
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }
}
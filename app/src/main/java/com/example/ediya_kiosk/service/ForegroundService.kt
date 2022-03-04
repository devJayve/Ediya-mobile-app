package com.example.ediya_kiosk.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.ediya_kiosk.BuildConfig
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import java.lang.StringBuilder
import java.util.ArrayList

class ForegroundService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var notiInformation = intent.getIntegerArrayListExtra("basketData")
        Log.d("Message", "notification $notiInformation")
        if (intent?.action != null && intent.action!!.equals(
                ACTION_STOP, ignoreCase = true)) {
                    stopForeground(true)
            stopSelf()
        }
        getBasketData(notiInformation)
        return START_NOT_STICKY
    }

    private var iconNotification: Bitmap? = null
    private var notification: Notification? = null
    var mNotificationManager: NotificationManager? = null
    private val title = "장바구니 정보"
    private val mNotificationId = 123

    private fun generateForegroundNotification(content : String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intentMainLanding = Intent(this, MainActivity::class.java)
            intentMainLanding.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


            var builder = NotificationCompat.Builder(this, "service_channel")
            builder.setSmallIcon(R.mipmap.ic_main)
            builder.setContentTitle(StringBuilder(title))
                .setContentText(content).priority = NotificationCompat.PRIORITY_DEFAULT

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

            builder.setContentIntent(pendingIntent)

            notification = builder.build()
            startForeground(mNotificationId, notification)
        }
    }

    fun getBasketData(notiInformation: ArrayList<Int>?) {
        if (notiInformation != null) {
            var menuNum = notiInformation[0]
            var menuPrice = notiInformation[1]
            var content = "장바구니 메뉴 개수 : $menuNum | 총 가격 : $menuPrice 원"
            Log.d("Message", "$content")
            generateForegroundNotification(content)
        }
        else generateForegroundNotification("장바구니 메뉴 개수 : 0 | 총 가격 : 0 원")


    }

    companion object{
        const val  ACTION_STOP = "${BuildConfig.APPLICATION_ID}.stop"
    }
}
package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {

    lateinit var backgroundMusic : MediaPlayer

    override fun onBind(intent: Intent): IBinder? { // on Bind 사용 시 Bind 서비스
        return null
    }

    override fun onCreate() {
        super.onCreate()
        backgroundMusic = MediaPlayer.create(this, R.raw.bgm)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //서비스가 실행될 때

        // TO::DO 내가 서비스를 통해 하고 싶은 것
        backgroundMusic.start()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()

        backgroundMusic.stop()
    }
}
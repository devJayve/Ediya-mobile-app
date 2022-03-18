package com.example.ediya_kiosk

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class EdiyaApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {


        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}
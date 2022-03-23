package com.example.ediya_kiosk

import android.app.Application
import com.example.ediya_kiosk.database.PreferenceUtil

class EdiyaApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {


        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}
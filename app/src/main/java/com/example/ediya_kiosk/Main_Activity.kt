package com.example.ediya_kiosk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_layout)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()

        initEvent()

    }

    fun initEvent() {
        var menuListBtn = findViewById<Button>(R.id.menuListBtn)
        var confirmBtn = findViewById<Button>(R.id.confirmBtn)

        menuListBtn!!.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }

        confirmBtn!!.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, ConfirmFragment()).commit()
        }
    }
}
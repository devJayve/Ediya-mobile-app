package com.example.ediya_kiosk

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ediya_kiosk.service.ForegroundService
import kotlinx.android.synthetic.main.start_layout.*

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_layout)

        var takeInBtn = findViewById<Button>(R.id.takeInBtn)
        var takeOutBtn = findViewById<Button>(R.id.takeOutBtn)

        takeInBtn!!.setOnClickListener {
            clickTakeBtn(takeInBtn)
        }

        takeOutBtn!!.setOnClickListener {
            clickTakeBtn(takeOutBtn)

        }
    }

        private fun clickTakeBtn(Btn: Button) {
            when (Btn) {
                Btn -> takeInBtn // 인텐트에 테이크인 정보 포함
                Btn -> takeOutBtn // 인텐트에 테이크아웃 정보 포함
            }
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }
    }



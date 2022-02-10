package com.example.ediya_kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.start_layout)

        goMainEvent()
    }

    fun goMainEvent() {
        var takeInBtn = findViewById<Button>(R.id.takeInBtn)
        var takeOutBtn = findViewById<Button>(R.id.takeOutBtn)

        takeInBtn!!.setOnClickListener {
            val nextIntent_1 = Intent(this, MainActivity::class.java)
            startActivity(nextIntent_1)
        }

        takeOutBtn!!.setOnClickListener {
            val nextIntent_2 = Intent(this, MainActivity::class.java)
            startActivity(nextIntent_2)
        }
    }
}

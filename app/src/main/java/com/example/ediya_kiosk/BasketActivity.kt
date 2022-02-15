package com.example.ediya_kiosk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ediya_kiosk.fragment.basket_fragment

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.basket_layout)
        supportFragmentManager.beginTransaction().replace(R.id.basket_fragmentArea, basket_fragment()).commit()
    }
}
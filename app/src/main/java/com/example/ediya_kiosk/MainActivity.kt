package com.example.ediya_kiosk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_layout)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, coffee_fragment()).commit()

        initEvent()

    }

    fun initEvent() {
        var coffeeBtn = findViewById<Button>(R.id.coffeeBtn)
        var beverageBtn = findViewById<Button>(R.id.beverageBtn)
        var blendingTeaBtn = findViewById<Button>(R.id.blendingTeaBtn)
        var aidBtn = findViewById<Button>(R.id.aidBtn)
        var shakeBtn = findViewById<Button>(R.id.shakeBtn)
        var platinoBtn = findViewById<Button>(R.id.platinoBtn)
        var bubbleMilkTeaBtn = findViewById<Button>(R.id.bubbleMilkTeaBtn)
        var bakeryBtn = findViewById<Button>(R.id.bakeryBtn)


        coffeeBtn!!.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, coffee_fragment()).commit()
        }

        beverageBtn!!.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, beverage_fragment()).commit()
        }
    }
}
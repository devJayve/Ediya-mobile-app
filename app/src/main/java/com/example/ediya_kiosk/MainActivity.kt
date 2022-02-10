package com.example.ediya_kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_layout)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, coffee_fragment).commit()

        initEvent()
        goBasketEvent()
        goMembershipEvent()
    }

    fun initEvent() {
        var coffeeBtn = findViewById<Button>(R.id.coffeeBtn)
        var beverageBtn = findViewById<Button>(R.id.beverageBtn)
        var blendingTeaBtn = findViewById<Button>(R.id.blendingTeaBtn)
        var adeBtn = findViewById<Button>(R.id.adeBtn)
        var shakeBtn = findViewById<Button>(R.id.shakeBtn)
        var flatccinoBtn = findViewById<Button>(R.id.flatccinoBtn)
        var bubbleMilkTeaBtn = findViewById<Button>(R.id.bubbleMilkTeaBtn)
        var bakeryBtn = findViewById<Button>(R.id.bakeryBtn)

        val categoryBtnMap = mapOf(
            coffeeBtn to coffee_fragment,
            beverageBtn to beverage_fragment,
            blendingTeaBtn to blendingTea_fragment,
            adeBtn to ade_fragment,
            shakeBtn to shake_fragment,
            flatccinoBtn to flatccino_fragment,
            bubbleMilkTeaBtn to bubbleMilkTea_fragment,
            bakeryBtn to bakery_fragment)

        for ((btn, btn_fragment) in categoryBtnMap) {
            btn!!.setOnClickListener {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, btn_fragment).commit()
            }
        }
    }

    fun goBasketEvent() {
        var basketBtn = findViewById<Button>(R.id.basketBtn)

        basketBtn!!.setOnClickListener {
            val basketIntent = Intent(this, BasketActivity::class.java)
            startActivity(basketIntent)
        }
    }

    fun goMembershipEvent() {
        var membershipBtn = findViewById<Button>(R.id.paymentBtn)

        membershipBtn!!.setOnClickListener {
            val membershipIntent = Intent(this, MembershipActivity::class.java)
            startActivity(membershipIntent)
        }
    }
}

package com.example.ediya_kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ediya_kiosk.fragment.*
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var myadapter: MainRvAdapter

    // 데이터를 담는 배열
    var menuList = ArrayList<MenuData>()

    // 뷰를 화면에 띄워줌
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)


        //호출 함수
        initRecyclerView()
        initEvent()
        goBasketEvent()
        goMembershipEvent()
    }

    fun initRecyclerView() {
        for (i in 1..10){
            val menuData = MenuData("COFFEE","아메리카노 $i","3500", "")
            this.menuList.add(menuData)

            //어답터 인스턴스 생성
            myadapter = MainRvAdapter()

            myadapter.submitList(this.menuList)

            // 리사이클뷰 설정
            menuRecycleView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

                //어답터 장착
                adapter = myadapter
            }

        }
    }

    fun initEvent() {
        var coffeeBtn = findViewById<Button>(R.id.coffeeBtn)
        var beverageBtn = findViewById<Button>(R.id.beverageBtn)
        var blendingTeaBtn = findViewById<Button>(R.id.blendingTeaBtn)
        var adeBtn = findViewById<Button>(R.id.adeBtn)
        var shakeBtn = findViewById<Button>(R.id.shakeBtn)
        var flatccinoBtn = findViewById<Button>(R.id.flatccinoBtn)
        var bubbleMilkTeaBtn = findViewById<Button>(R.id.bubbleMilkTeaBtn)

        val categoryBtnMap = mapOf(
            coffeeBtn to coffee_fragment,
            beverageBtn to beverage_fragment,
            blendingTeaBtn to blendingTea_fragment,
            adeBtn to ade_fragment,
            shakeBtn to shake_fragment,
            flatccinoBtn to flatccino_fragment,
            bubbleMilkTeaBtn to bubbleMilkTea_fragment,
        )

//        for ((btn, btn_fragment) in categoryBtnMap) {
//            btn!!.setOnClickListener {
//                supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, btn_fragment).commit()
//            }
//        }
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

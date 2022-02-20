package com.example.forlecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.myapplication.R

class MainActivity : AppCompatActivity(), DataInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        Log.d("Message","OnCreate")
        //액티비티-> fragment 데이터 보내기
        //초기 화면에 들어갈 fragment
        toMenuListFragment()
        
        initEvent()
    }

    fun initEvent() {
        val menuListBtn = findViewById<Button>(R.id.menulistbtn)
        menuListBtn.setOnClickListener {
           toMenuListFragment()
        }
        val confirmBtn = findViewById<Button>(R.id.confirmbtn)
        confirmBtn.setOnClickListener {
            toConfirmFragment()
        }
    }

    fun toConfirmFragment(){
        var fragment = ConfirmFragment()
        var myBundle = Bundle()
        myBundle.putString("title","컨펌으로 이동")
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.framentArea,fragment).commit()
    }

    fun toMenuListFragment(){
        var fragment = MenulistFragment()
        var myBundle = Bundle()
        myBundle.putString("title","메뉴 리스트로 이동")
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.framentArea,fragment).commit()
    }

    override fun datapass(data: String) {
        Log.d("Message",data)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Message","OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Message","OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Message","OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Message","OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Message","OnDestroy")
    }
}
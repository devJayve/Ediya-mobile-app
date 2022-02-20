package com.example.ediya_kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ediya_kiosk.fragment.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        var fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_area, fragment).commit()
    }

    fun openOtherFragment(int: Int){
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            1 -> {
                transaction.replace(R.id.fragment_area, basket_fragment())
                transaction.addToBackStack(null)
            }
            3 -> {
                transaction.replace(R.id.fragment_area, menu_detail_fragment())
                transaction.addToBackStack(null)
            }
        }
        transaction.commit()
    }

    fun openOtherFragmentforBundle(int: Int,frag : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        when(int){
            4 -> {
                transaction.replace(R.id.fragment_area, frag)
                transaction.addToBackStack(null)
            }
        }
        transaction.commit()
    }
}

package com.example.ediya_kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ediya_kiosk.fragment.*
import kotlinx.android.synthetic.main.login_layout.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_layout)

        var fragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.start_fragment_area, fragment).commit()
    }

    fun login() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    fun register(step : Int) {
        var transaction = this.supportFragmentManager.beginTransaction()
        var registerFrag1 = RegisterFirstFragment()
        var registerFrag2 = RegisterSecondFragment()
        var registerFrag3 = RegisterThirdFragment()
        var registerFrag4 = RegisterForthFragment()
        when (step) {
            1 -> {
                transaction.add(R.id.start_fragment_area, registerFrag1)
                transaction.addToBackStack(null)
            }
            2 -> {
                transaction.add(R.id.start_fragment_area, registerFrag2)
                transaction.addToBackStack(null)
            }
            3 -> {
                transaction.add(R.id.start_fragment_area, registerFrag3)
                transaction.addToBackStack(null)
            }
            4 -> {
                transaction.replace(R.id.start_fragment_area, registerFrag4)
            }
            5 -> {
                transaction.replace(R.id.start_fragment_area, LoginFragment())
            }
        }
        transaction.commit()
    }
}





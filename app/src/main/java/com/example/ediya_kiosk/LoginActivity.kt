package com.example.ediya_kiosk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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
        var register_frag_1 = RegisterFragment_1st()
        var register_frag_2 = RegisterFragment_2nd()
        var register_frag_3 = RegisterFragment_3rd()
        var register_frag_4 = RegisterFragment_4th()
        when (step) {
            1 -> {
                transaction.add(R.id.start_fragment_area, register_frag_1)
                transaction.addToBackStack(null)
            }
            2 -> {
                transaction.add(R.id.start_fragment_area, register_frag_2)
                transaction.addToBackStack(null)
            }
            3 -> {
                transaction.add(R.id.start_fragment_area, register_frag_3)
                transaction.addToBackStack(null)
            }
            4 -> {
                transaction.replace(R.id.start_fragment_area, register_frag_4)
            }
            5 -> {
                transaction.replace(R.id.start_fragment_area, LoginFragment())
            }
        }
        transaction.commit()
    }
}





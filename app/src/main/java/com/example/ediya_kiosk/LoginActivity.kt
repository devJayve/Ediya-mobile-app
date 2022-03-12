package com.example.ediya_kiosk

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amitshekhar.DebugDB
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import com.example.ediya_kiosk.fragment.*
import kotlinx.android.synthetic.main.login_layout.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.empty_layout)

        var fragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.start_fragment_area, fragment)
            .commit()
    }

    var db = Database(this, "ediya.db",null,1)

    fun login(id : String) {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.putExtra("id",id)
        startActivity(mainIntent)
    }

    fun register(step: Int) {
        var transaction = this.supportFragmentManager.beginTransaction()
        var registerFrag1 = RegisterFirstFragment()
        var registerFrag2 = RegisterSecondFragment()
        var registerFrag3 = RegisterThirdFragment()
        var registerFrag4 = RegisterForthFragment()
        when (step) {
            1 -> {
                transaction.replace(R.id.start_fragment_area, registerFrag1)
            }
            2 -> {
                transaction.replace(R.id.start_fragment_area, registerFrag2)
            }
            3 -> {
                transaction.replace(R.id.start_fragment_area, registerFrag3)
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




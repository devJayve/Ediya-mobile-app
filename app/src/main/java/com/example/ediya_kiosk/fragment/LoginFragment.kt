package com.example.ediya_kiosk.fragment

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import com.example.ediya_kiosk_Logic.main
import kotlinx.android.synthetic.main.login_layout.*

class LoginFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity
    private var isExistBlank = false
    private val db = Database(loginActivity, "login.db",null,1) // 대이터베이스 class 객체 선언
    private val readableDb = db.readableDatabase // 데이터베이스 객체를 읽기 가능 상태로 만듦
    private val writableDb = db.writableDatabase // 데이터베이스 객체를 쓰기 가능 상태로 만듦


    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.login_layout, container, false)

        var loginBtn = view?.findViewById<Button>(R.id.loginBtn)
        var registerBtn = view?.findViewById<Button>(R.id.registerBtn)

        loginBtn!!.setOnClickListener {
            loginEvent(view)
        }

        registerBtn!!.setOnClickListener {
            loginActivity!!.register(1)
        }

        return view
    }

    fun loginEvent(view: View) {
        var loginId = view.findViewById<EditText>(R.id.idET).text.toString()
        var loginPw = view.findViewById<EditText>(R.id.pwET).text.toString()

        var columnList = arrayListOf("id","password")
        var selectionList = arrayListOf("id","password")
        var selectionArgsList = arrayListOf("$loginId","$loginPw")

        val dbControl = DatabaseControl()
        val dataList = dbControl.readData(readableDb,"user",columnList,selectionList,selectionArgsList)

        if (loginId.isEmpty() || loginPw.isEmpty()) {
            isExistBlank = true
            Toast.makeText(loginActivity,"아이디 또는 비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show()

        }
        else if (dataList.size == 0) {
            loginActivity!!.login()
        }
    }
}
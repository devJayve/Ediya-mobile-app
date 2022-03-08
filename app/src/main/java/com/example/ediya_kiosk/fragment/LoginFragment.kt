package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import kotlinx.android.synthetic.main.login_layout.*

class LoginFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity
    private var isExistBlank = false

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

        if (loginId.isEmpty() || loginPw.isEmpty()) {
            isExistBlank = true
        }
        if
        loginActivity!!.login()

    }
}
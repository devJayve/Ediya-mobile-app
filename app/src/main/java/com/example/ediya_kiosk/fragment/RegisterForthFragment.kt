package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.R

class RegisterForthFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.register_layout_4, container, false)

        val nextPageBtn = view.findViewById<Button>(R.id.registerFinishBtn)
        nextPageBtn.setOnClickListener {
            loginActivity!!.register(5)
        }

        return view
    }
}
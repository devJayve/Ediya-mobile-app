package com.example.myapplication
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class ConfirmFragment: Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.main_confirm_fragment,container,false)

        var requestValue = arguments?.getString("title")

        val serviceBtn = view.findViewById<Button>(R.id.service_btn)
        serviceBtn!!.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.musicServiceStart()
        }

        Log.d("Message",requestValue!!)

        return view
    }
}
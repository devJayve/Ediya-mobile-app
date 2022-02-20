package com.example.forlecture
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class ConfirmFragment: Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var requestValue = arguments?.getString("title")

        Log.d("Message",requestValue!!)

        return inflater.inflate(R.layout.main_confirm_fragment,container,false)
    }
}
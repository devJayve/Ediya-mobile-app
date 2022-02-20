package com.example.forlecture

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class MenulistFragment: Fragment() {
    lateinit var dataInterface : DataInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataInterface = context as DataInterface
    }
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view = inflater.inflate(R.layout.main_menulist_fragment,container,false)

        //argumets = getarguments
        var requestValue = arguments?.getString("title")

        Log.d("Message",requestValue!!)

        var toConfirm = view.findViewById<Button>(R.id.to_cofirm_btn)
        toConfirm.setOnClickListener {
            dataInterface.datapass("컨펌 페이지로 이동 요청")
        }
        return view
    }
}
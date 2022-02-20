package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.MenuData
import com.example.ediya_kiosk.R
import kotlinx.android.synthetic.*

class menu_detail_fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.menu_detail_layout,container,false)

        var menuName = arguments?.getString("name")
        var menuPrice = arguments?.getString("price")
        var nametxt = view.findViewById<TextView>(R.id.menuNameTV)
        var pricetxt = view.findViewById<TextView>(R.id.menuPriceTV)
        nametxt.setText(menuName)
        pricetxt.setText((menuPrice))

        return view
    }
}
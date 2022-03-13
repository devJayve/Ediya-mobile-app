package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import kotlinx.android.synthetic.main.basket_layout.*
import kotlinx.android.synthetic.main.menu_item.*

class basket_fragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.basket_layout,container,false)

        var userId = arguments?.getString("userId")
        var nameList = arguments?.getStringArrayList("menu_name")
        var countList = arguments?.getStringArrayList("menu_count")
        var tempList  = arguments?.getStringArrayList("menu_temp")
        var sizeList = arguments?.getStringArrayList("menu_size")
        var priceList = arguments?.getStringArrayList("menu_price")
        var imgList = arguments?.getStringArrayList("menu_img")
        var optionCostList = arguments?.getStringArrayList("option_cost")
        var totalPriceList = arguments?.getStringArrayList("total_cost")

        val container = view.findViewById<LinearLayout>(R.id.basket_container)

        setContent(container,nameList,tempList,sizeList,priceList,totalPriceList,imgList)

        // 뒤로 가기
        val backBtn = view.findViewById<Button>(R.id.backBtn)
         backBtn.setOnClickListener {
             mainActivity.loadFrag(0)
         }

        //payment 로 이동
        val paymentBtn = view.findViewById<Button>(R.id.orderBtnInBasket)
        paymentBtn.setOnClickListener {
            mainActivity.loadFrag(2)
        }

        val removeAllBtn = view.findViewById<Button>(R.id.removeAllBtn)
        removeAllBtn.setOnClickListener {
            mainActivity.deleteLocalDb()
            mainActivity.clearBindService()
            mainActivity.loadFrag(1)
        }

        var totalPriceTV = view.findViewById<TextView>(R.id.totalPriceTV)
        // 총 가격 구해주기
        var totalPriceTxt = 0
        if (totalPriceList != null) {
            for (price in totalPriceList!!) {
                totalPriceTxt += price.toInt()
            }
        }
        totalPriceTV?.text = totalPriceTxt.toString().plus("원")

        return view
    }

    private fun setContent(layout: LinearLayout?,
                           name:ArrayList<String>?,
                           temp:ArrayList<String>?,
                           size:ArrayList<String>?,
                           price:ArrayList<String>?,
                           totalPrice:ArrayList<String>?,
                           img:ArrayList<String>?) {

        val layoutInflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val containView = layoutInflater.inflate(R.layout.bakset_menu_layout, null)

        if (name != null) {
            for ((i) in (0 until name.size).withIndex()) {
                var menuName = containView.findViewById<TextView>(R.id.menuNameTV)
                var menuTemp = containView.findViewById<TextView>(R.id.tempTV)
                var menuSize = containView.findViewById<TextView>(R.id.sizeTV)
                var menuPrice = containView.findViewById<TextView>(R.id.basicPriceTV)
                var menuTotalPrice = containView.findViewById<TextView>(R.id.menuPriceTV)
                var menuImg = containView.findViewById<ImageView>(R.id.menuImgIV)

                menuName.text = name[i]
                menuTemp.text = temp!![i].plus(" |")
                menuSize.text = size!![i].plus(" |")
                menuPrice.text = price!![i].plus("원")
                menuTotalPrice.text = totalPrice!![i].plus("원")
                menuImg.setImageResource(img!![i].toInt())
                layout?.addView(containView)
            }
        }
    }
}
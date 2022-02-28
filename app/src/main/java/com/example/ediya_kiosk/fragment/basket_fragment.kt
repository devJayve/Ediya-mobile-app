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

        var nameList = arguments?.getStringArrayList("nameList")
        var tempList  = arguments?.getStringArrayList("tempList")
        var sizeList = arguments?.getStringArrayList("sizeList")
        var priceList = arguments?.getIntegerArrayList("priceList")
        var totalPriceList = arguments?.getIntegerArrayList("totalPriceList")
        var imgList = arguments?.getStringArrayList("imgList")
        val basketTotalPrice = view.findViewById<TextView>(R.id.totalPriceTV)

        val container = view.findViewById<LinearLayout>(R.id.basket_container)
        Log.d("Message","$nameList, $priceList 불러옴" )

        setContent(container,nameList,tempList,sizeList,priceList,totalPriceList,imgList,basketTotalPrice)

        // 뒤로 가기
        val backBtn = view.findViewById<Button>(R.id.backBtn)
         backBtn.setOnClickListener {
             mainActivity!!.openOtherFragmentforBundle(5,this)
         }

        return view
    }

    private fun setContent(layout: LinearLayout?, name:ArrayList<String>?, temp:ArrayList<String>?,size:ArrayList<String>?,
                            price:ArrayList<Int>?,totalPrice:ArrayList<Int>?,img:ArrayList<String>?,basketTotalPrice:TextView) {
        // 총 가격 구해주기
        var totalPriceTxt = 0
        for (price in totalPrice!!) {
            totalPriceTxt += price
        }
        basketTotalPrice.text = totalPriceTxt.toString().plus("원")

        if (name != null) {
            for ((i) in (0 until name.size).withIndex()) {
                val layoutInflater =
                    mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val containView = layoutInflater.inflate(R.layout.bakset_menu_layout, null)
                var menuName = containView.findViewById<TextView>(R.id.menuNameTV)
                var menuTemp = containView.findViewById<TextView>(R.id.tempTV)
                var menuSize = containView.findViewById<TextView>(R.id.sizeTV)
                var menuPrice = containView.findViewById<TextView>(R.id.basicPriceTV)
                var menuTotalPrice = containView.findViewById<TextView>(R.id.menuPriceTV)
                var menuImg = containView.findViewById<ImageView>(R.id.menuImgIV)

                menuName.text = name[i]
                menuTemp.text = temp!![i].plus(" |")
                menuSize.text = size!![i].plus(" |")
                menuPrice.text = price!![i].toString().plus("원")
                menuTotalPrice.text = totalPrice!![i].toString().plus("원")
                menuImg.setImageResource(img!![i].toInt())
                layout?.addView(containView)
            }
        }
    }
}
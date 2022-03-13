package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl

class OrderHistoryFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private lateinit var userId : String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.order_history_layout, container, false)

        val container = view.findViewById<LinearLayout>(R.id.history_container)
        userId = arguments?.getString("userId").toString()

        //data list
        var orderIndexList : ArrayList<String> = arrayListOf() //주문번호
        var menuNameList : ArrayList<String> = arrayListOf() //이름
        var menuCountList : ArrayList<String> = arrayListOf() //개수
        var menuPriceList : ArrayList<String> = arrayListOf() //가격

        //history 불러오기
        val db = Database(mainActivity, "ediya.db",null,1)
        val readableDb = db.readableDatabase
        val dbControl = DatabaseControl()
        var historyDataList = dbControl.readData(readableDb,
            "basket_order",
            arrayOf("order_index","menu_name","menu_price","menu_count"),
            arrayListOf("id"),
            arrayOf(userId)
        )
        var finalOrderIndex = historyDataList[-1][0].toInt()
        for (i in (0 until finalOrderIndex)) {
            dbControl.readData(
                readableDb,
                "basket_order",
                arrayOf("order_index", "menu_name", "menu_price", "menu_count"),
                arrayListOf("id", "order_index"),
                arrayOf(userId,i.toString())
            )


        }


        if (historyDataList.size > 0) {
            setHistoryContent(finalOrderIndex,historyDataList)
        } else {

        }

        return view
    }

    fun readBasketData() : ArrayList<ArrayList<String>> {
        val db = Database(mainActivity, "ediya.db", null, 1)
        val readableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        var menuNameList : ArrayList<String> = arrayListOf() //이름
        var menuCountList : ArrayList<String> = arrayListOf() //개수
        var menuTempList : ArrayList<String> = arrayListOf() //온도
        var menuSizeList : ArrayList<String> = arrayListOf() //사이즈
        var menuPriceList : ArrayList<String> = arrayListOf() //단품 가격
        var menuImgList : ArrayList<String> = arrayListOf() //이미지
        var optionCostList : ArrayList<String> = arrayListOf() //옵션 가격
        var totalCostList : ArrayList<String> = arrayListOf() //메뉴 총 가격
        var menuDataList = arrayListOf(
            menuNameList,
            menuCountList,
            menuTempList,
            menuSizeList,
            menuPriceList,
            menuImgList,
            optionCostList,
            totalCostList)

        var basketData = dbControl.readData(
            readableDb,
            "basket",
            arrayOf("menu_name",
                "menu_count",
                "menu_temp",
                "menu_size",
                "menu_price",
                "menu_img",
                "option_cost",
                "total_cost"),
            arrayListOf("id"),
            arrayOf(userId)
        )

        if (basketData.size > 0) {
            for (i in basketData[0].indices) {
                for (j in basketData.indices) {
                    menuDataList[i].add(basketData[j][i])
                }
            }
            for (i in menuDataList.indices) {
                Log.d("TAG", "${menuDataList[i]}")
            }
        }
        return menuDataList
    }

    private fun setHistoryContent(orderCount : Int,DataList : ArrayList<ArrayList<String>>) {

        val layoutInflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val containView = layoutInflater.inflate(R.layout.order_history_layout, null)

        for (i in (0 until orderCount)) {
            var orderIndex = containView.findViewById<TextView>(R.id.history_order_indexTV)
            var menuName = containView.findViewById<TextView>(R.id.history_menu_nameTV)
            var payKind = containView.findViewById<TextView>(R.id.history_pay_kindTV)
            var payCost = containView.findViewById<TextView>(R.id.history_pay_costTV)
            var orderState = containView.findViewById<TextView>(R.id.history_order_stateTV)

                orderIndex.text = DataList[i][0]
                menuName.text = DataList[i][1]
                payKind.text = "이디야페이"



        }
    }
}
package com.example.ediya_kiosk.fragment

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl

class OrderHistoryFragment : Fragment() {

    private lateinit var mainActivity: MainActivity
    private val userId = arguments?.getString("userId").toString()

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

        val historyContainer = view.findViewById<LinearLayout>(R.id.history_container)
        val backBtn = view.findViewById<ImageButton>(R.id.backToMainBtn2)

        // 기록 불러오기
        setHistoryContent(historyContainer)

        //뒤로 가기
        backBtn.setOnClickListener {
            mainActivity.loadFrag(0)
        }

        return view
    }

    private fun readBasketData() : Pair<ArrayList<ArrayList<String>>,ArrayList<ArrayList<String>>> {
        val db = Database(mainActivity, "ediya.db", null, 1)
        val readableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        val basketIndexList : ArrayList<String> = arrayListOf()
        val menuNameList : ArrayList<String> = arrayListOf() //이름
        val menuCountList : ArrayList<String> = arrayListOf() //개수
        val menuTempList : ArrayList<String> = arrayListOf() //온도
        val menuSizeList : ArrayList<String> = arrayListOf() //사이즈
        val menuPriceList : ArrayList<String> = arrayListOf() //단품 가격
        val menuImgList : ArrayList<String> = arrayListOf() //이미지
        val optionCostList : ArrayList<String> = arrayListOf() //옵션 가격
        val totalCostList : ArrayList<String> = arrayListOf() //메뉴 총 가격
        val menuDataList = arrayListOf(
            basketIndexList,
            menuNameList,
            menuCountList,
            menuTempList,
            menuSizeList,
            menuPriceList,
            menuImgList,
            optionCostList,
            totalCostList
        )

        var basketDataList = dbControl.readData(
            readableDb,
            "basket",
            arrayOf(
                "order_index",
                "menu_name",
                "menu_count",
                "menu_temp",
                "menu_size",
                "menu_price",
                "menu_img",
                "option_cost",
                "total_cost"
            ),
            arrayListOf("id"),
            arrayOf(userId)
        )
        for (i in basketDataList.indices) {
            menuDataList[0].add(basketDataList[i][0])
            menuDataList[1].add(basketDataList[i][1])
            menuDataList[2].add(basketDataList[i][2])
            menuDataList[3].add(basketDataList[i][3])
            menuDataList[4].add(basketDataList[i][4])
            menuDataList[5].add(basketDataList[i][5])
            menuDataList[6].add(basketDataList[i][6])
            menuDataList[7].add(basketDataList[i][7])
            menuDataList[8].add(basketDataList[i][8])
            menuDataList[9].add(basketDataList[i][9])
        }

        val paymentDataList = dbControl.readData(
            readableDb,
            "payment",
            arrayOf("order_index","discount","payment"),
            arrayListOf("id"),
            arrayOf(userId)
        )

        return Pair(basketDataList,paymentDataList)
    }

    private fun setHistoryContent(container: LinearLayout) {
        val (basketDataList, paymentDataList) = readBasketData()
        val db = Database(mainActivity, "ediya.db", null, 1)
        val readableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        val layoutInflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val containView = layoutInflater.inflate(R.layout.order_history_layout, null)

        for (i in (0 until paymentDataList[-1][0].toInt())) {
            val orderIndex = containView.findViewById<TextView>(R.id.history_order_indexTV)
            val menuName = containView.findViewById<TextView>(R.id.history_menu_nameTV)
            val payKind = containView.findViewById<TextView>(R.id.history_pay_kindTV)
            val payCost = containView.findViewById<TextView>(R.id.history_pay_costTV)
            val orderState = containView.findViewById<TextView>(R.id.history_order_stateTV)
            val intoInfoBtn = containView.findViewById<ImageButton>(R.id.into_info_btn)

            intoInfoBtn.setOnClickListener {
                Toast.makeText(mainActivity,"$i infoBtn clicked",Toast.LENGTH_SHORT).show()
            }

            val orderList = dbControl.readData(readableDb, "basket", arrayOf("order_index","menu_name","menu_temp","total_cost"), arrayListOf("order_index"),arrayOf("$i"))
            var totalCost = 0
            for (i in orderList.indices) {
                totalCost += orderList[i][1].toInt()
            }
            totalCost -= orderList[i][1].toInt()

            orderIndex.text = orderList[i][0]
            "${orderList[i][2]} ${(orderList[i][1])} 외 ${orderList.size-1}건".also { menuName.text = it }
            payKind.text = paymentDataList[i][2]
            payCost.text = "$totalCost".plus("원")
            orderState.text = "픽업 완료"

            container.addView(containView)
        }
    }
}
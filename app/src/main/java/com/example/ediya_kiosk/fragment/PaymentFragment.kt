package com.example.ediya_kiosk.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.CouponOptionDialog
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import com.example.ediya_kiosk.optionDialog
import kotlinx.android.synthetic.main.menu_detail_layout.*
import kotlinx.android.synthetic.main.payment_layout.*

class PaymentFragment : Fragment() {

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
        var view = inflater.inflate(R.layout.payment_layout, container, false)

        userId = arguments?.getString("userId").toString()
        Log.d("TAG","user id is $userId")

        val menuNameList = arguments?.getStringArrayList("menu_name")
        val menuCountList = arguments?.getStringArrayList("menu_count")
        val menuTempList = arguments?.getStringArrayList("menu_temp")
        val menuSizeList = arguments?.getStringArrayList("menu_size")
        var menuPriceList = arguments?.getStringArrayList("menu_price")
        val menuImgList = arguments?.getStringArrayList("menu_img")
        val optionCostList = arguments?.getStringArrayList("option_cost")
        val totalCostList = arguments?.getStringArrayList("total_cost")
        var menuDataList = arrayListOf(
            menuNameList,
            menuCountList,
            menuTempList,
            menuSizeList,
            menuPriceList,
            menuImgList,
            optionCostList,
            totalCostList
        )

        var pointTV = view.findViewById<TextView>(R.id.point_TV)

        val ediyaPayBtn = view.findViewById<Button>(R.id.ediyaPayBtn)
        val otherPayBtn = view.findViewById<Button>(R.id.otherPayBtn)
        var orderBtn = view.findViewById<Button>(R.id.paymentBtnInPayment)
        val backBtn = view.findViewById<ImageButton>(R.id.backToMainBtn)
        val couponBtn = view.findViewById<Button>(R.id.showCouponBtn)

        var discount = 0
        var payment : String

        //db
        val db = Database(mainActivity, "ediya.db",null,1)
        val readableDb = db.readableDatabase
        val writableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        val payContainer = view.findViewById<HorizontalScrollView>(R.id.ediyaPayContainer)
        val otherPayContainer = view.findViewById<RadioGroup>(R.id.otherPayContainer)

        payContainer.visibility = View.GONE
        otherPayContainer.visibility = View.GONE

        //coupon
        var couponNum : Int? = null
        couponBtn.setOnClickListener {
            Log.d("Message", "click couponBtn $couponNum")
            val dialog = CouponOptionDialog(mainActivity,couponNum)
            dialog.showDialog(mainActivity)
            dialog.setOnClickListener(object : CouponOptionDialog.OnDialogClickListener {
                override fun onClicked(num: Int) {
                    Log.d("Message", "onClicked coupon num $num")
                    couponNum = num
                }
            })
        }

        //point
        var point = dbControl.readData(readableDb,"account", arrayOf("point"), arrayListOf("id"), arrayOf(userId)).toString()
        pointTV.text = point

        //back btn
        backBtn.setOnClickListener {
            val backDialog = AlertDialog.Builder(mainActivity)
            backDialog.setMessage("결제를 취소하시겠습니까 ?")
            backDialog.setPositiveButton("네", DialogInterface.OnClickListener { _, _ ->
                mainActivity.loadFrag(1)
            })
            backDialog.setNegativeButton("취소",null)
            backDialog.show()
        }

        ediyaPayBtn.setOnClickListener {
            setPayContent(ediyaPayBtn)
        }

        otherPayBtn.setOnClickListener {
            setPayContent(otherPayBtn)
        }

        //결제 완료
        orderBtn.setOnClickListener {
            Log.d("Message","orderBtn click")

            addOrderHistory(menuDataList)
            mainActivity!!.loadFrag(3)
            Toast.makeText(mainActivity,"주문이 완료되었습니다.",Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun setPayContent (btn : Button) {
        btn?.isSelected = true
        if (btn == ediyaPayBtn) {
            otherPayBtn.isSelected = false
            ediyaPayContainer.visibility = View.VISIBLE
            otherPayContainer.visibility = View.GONE
            }
        else {
            ediyaPayBtn.isSelected = false
            otherPayContainer.visibility = View.VISIBLE
            ediyaPayContainer.visibility = View.GONE
        }
    }

    private fun addOrderHistory(menuDataList : ArrayList<ArrayList<String>?>) {
        val db = Database(mainActivity, "ediya.db",null,1)
        val writableDb = db.writableDatabase
        val readableDb = db.readableDatabase
        val dbControl = DatabaseControl()

        var orderIndex = "-1"
        var orderIndexList = dbControl.readData(readableDb, "basket", arrayOf("order_index"), arrayListOf("id"), arrayOf(userId))
        Log.d("TAG","$orderIndexList")
        if (orderIndexList.size != 0) {
            orderIndex = orderIndexList.last()[0]
        }

        for (i in menuDataList[0]!!.indices) {
            dbControl.createData(
                writableDb,
                "basket",
                arrayListOf(
                    "id",
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
                arrayListOf(
                    userId,
                    (orderIndex.toInt()+1).toString(),
                    menuDataList[0]!![i], //name
                    menuDataList[1]!![i], //count
                    menuDataList[2]!![i], //temp
                    menuDataList[3]!![i], //size
                    menuDataList[4]!![i], //price
                    menuDataList[5]!![i], //img
                    menuDataList[6]!![i], //option_cost
                    menuDataList[7]!![i], //total_cost
                )
            )
        }

        dbControl.createData(
            writableDb,
            "payment",
            arrayListOf("id",
                "order_index",
                "discount",
                "payment"),
            arrayListOf(userId,
                (orderIndex.toInt()+1).toString(),
                "0",
                "0")
        )
    }


    private fun loadAlertDialog(message : String) {
        val phoneDialogBuilder = AlertDialog.Builder(mainActivity)
        //phoneDialogBuilder.setTitle("Error")
        phoneDialogBuilder.setMessage("$message")
        phoneDialogBuilder.setPositiveButton("확인",null)
        phoneDialogBuilder.show()
    }
}

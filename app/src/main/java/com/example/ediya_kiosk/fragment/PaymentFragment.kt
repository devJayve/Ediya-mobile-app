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

        val userId = arguments?.getString("userId").toString()
        val menuNameList = arguments?.getStringArrayList("menu_name")
        val menuPriceList = arguments?.getStringArrayList("total_cost")
        val menuCountList = arguments?.getStringArrayList("menu_count")

        val ediyaPayBtn = view.findViewById<Button>(R.id.ediyaPayBtn)
        val otherPayBtn = view.findViewById<Button>(R.id.otherPayBtn)
        var orderBtn = view.findViewById<Button>(R.id.paymentBtnInPayment)
        val backBtn = view.findViewById<ImageButton>(R.id.backToMainBtn)
        val couponBtn = view.findViewById<Button>(R.id.showCouponBtn)

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


        //back btn
        backBtn.setOnClickListener {
            val backDialog = AlertDialog.Builder(mainActivity)
            backDialog.setMessage("결제를 취소하시겠습니까 ?")
            backDialog.setPositiveButton("확인",null)
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
            val db = Database(mainActivity, "ediya.db",null,1)
            val readableDb = db.readableDatabase
            val writableDb = db.writableDatabase
            val dbControl = DatabaseControl()

            var orderIndexList = dbControl.readData(readableDb,"basket_order", arrayOf("order_index"), arrayListOf("id"), arrayOf(userId))
            var index = orderIndexList[-1][0]

            for (i in menuNameList!!.indices) {
                dbControl.createData(
                    writableDb,
                    "basket_order",
                    arrayListOf("id", "order_index", "menu_name", "menu_price", "menu_count"),
                    arrayListOf(userId, index+1,menuNameList[i],menuPriceList!![i],menuCountList!![i])
                )
            }
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


    private fun loadAlertDialog(message : String) {
        val phoneDialogBuilder = AlertDialog.Builder(mainActivity)
        //phoneDialogBuilder.setTitle("Error")
        phoneDialogBuilder.setMessage("$message")
        phoneDialogBuilder.setPositiveButton("확인",null)
        phoneDialogBuilder.show()
    }
}

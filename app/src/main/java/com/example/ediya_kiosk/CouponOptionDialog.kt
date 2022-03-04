package com.example.ediya_kiosk

import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import kotlinx.android.synthetic.main.cost_option_dialog_layout.*
import android.content.Context as Context

class CouponOptionDialog(context: Context)
{
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(mainActivity: MainActivity) {
        dialog.setContentView(R.layout.coupon_dialog_layout)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val couponBtn1 = dialog.findViewById<Button>(R.id.couponBtn1)
        val couponBtn2 = dialog.findViewById<Button>(R.id.couponBtn2)
        val couponBtn3 = dialog.findViewById<Button>(R.id.couponBtn3)
        val couponBtn4 = dialog.findViewById<Button>(R.id.couponBtn4)
        val couponBtn5 = dialog.findViewById<Button>(R.id.couponBtn5)
        val couponBtnList = listOf<Button>(couponBtn1,couponBtn2,couponBtn3,couponBtn4,couponBtn5)


        dialog.show()

        for (i in 0..4) {
            couponBtnList[i]?.setOnClickListener {
                Toast.makeText(mainActivity, "쿠폰이 선택되었습니다.", Toast.LENGTH_SHORT).show()
                onClickListener.onClicked(10)
                dialog.dismiss()
            }
        }
    }



    interface OnDialogClickListener
    {
        fun onClicked(cost: Int)
    }
}
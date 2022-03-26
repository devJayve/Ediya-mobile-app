package com.example.ediya_kiosk.dialog

import android.app.Dialog
import android.content.Context
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.activity.MainActivity

class ReceiptDialog(context: Context,orderNum : Int) {

    private val dialog = Dialog(context)
    private lateinit var onClickListener: ReceiptDialog.OnDialogClickListener

    fun setOnClickListener(listener: ReceiptDialog.OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(mainActivity: MainActivity) {
        dialog.setContentView(R.layout.receipt_dialog_layout)








    }



    interface OnDialogClickListener
    {
        fun onClicked(cost: Int)
    }
}
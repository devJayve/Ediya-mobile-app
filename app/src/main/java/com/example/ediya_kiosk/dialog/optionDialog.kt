package com.example.ediya_kiosk.dialog

import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import com.example.ediya_kiosk.activity.MainActivity
import com.example.ediya_kiosk.R
import kotlinx.android.synthetic.main.cost_option_dialog_layout.*
import android.content.Context as Context

class optionDialog(context: Context,num : Int)
{
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog(mainActivity: MainActivity) {
        dialog.setContentView(R.layout.cost_option_dialog_layout)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        // plus Btn
        val plusBtn1 = dialog.findViewById<ImageButton>(R.id.optionPlusBtn_1)
        val plusBtn2 = dialog.findViewById<ImageButton>(R.id.optionPlusBtn_2)
        val plusBtn3 = dialog.findViewById<ImageButton>(R.id.optionPlusBtn_3)
        val plusBtn4 = dialog.findViewById<ImageButton>(R.id.optionPlusBtn_4)
        val plusBtn5 = dialog.findViewById<ImageButton>(R.id.optionPlusBtn_5)
        val plusBtnList = listOf<ImageButton>(plusBtn1, plusBtn2, plusBtn3, plusBtn4, plusBtn5)


        // minus Btn
        val minusBtn1 = dialog.findViewById<ImageButton>(R.id.optionMinusBtn_1)
        val minusBtn2 = dialog.findViewById<ImageButton>(R.id.optionMinusBtn_2)
        val minusBtn3 = dialog.findViewById<ImageButton>(R.id.optionMinusBtn_3)
        val minusBtn4 = dialog.findViewById<ImageButton>(R.id.optionMinusBtn_4)
        val minusBtn5 = dialog.findViewById<ImageButton>(R.id.optionMinusBtn_5)
        val minusBtnList =
            listOf<ImageButton>(minusBtn1, minusBtn2, minusBtn3, minusBtn4, minusBtn5)


        //output txt
        val outputNum1 = dialog.findViewById<TextView>(R.id.optionQuantity_1)
        val outputNum2 = dialog.findViewById<TextView>(R.id.optionQuantity_2)
        val outputNum3 = dialog.findViewById<TextView>(R.id.optionQuantity_3)
        val outputNum4 = dialog.findViewById<TextView>(R.id.optionQuantity_4)
        val outputNum5 = dialog.findViewById<TextView>(R.id.optionQuantity_5)
        val outputNumList =
            listOf<TextView>(outputNum1, outputNum2, outputNum3, outputNum4, outputNum5)

        // number
        var number1 = 0
        var number2 = 0
        var number3 = 0
        var number4 = 0
        var number5 = 0
        val numberList = mutableListOf<Int>(number1, number2, number3, number4, number5)

        var optionCost = 0

        dialog.show()

        for (i in 0..4) {
            plusBtnList[i]?.setOnClickListener {
                if (numberList[i] == 9) {
                    !plusBtnList[i].isClickable
                } else {
                    plusBtnList[i].isClickable
                    numberList[i]++
                    optionCost += 500
                    outputNumList[i].setText(numberList[i].toString())
                }
            }
        }

        for (i in 0..4) {
            minusBtnList[i]?.setOnClickListener {
                if (numberList[i] == 0) {
                    !minusBtnList[i].isClickable
                } else {
                    minusBtnList[i].isClickable
                    numberList[i]--
                    optionCost -= 500
                    outputNumList[i].setText(numberList[i].toString())
                }
            }


            dialog.choseBtn.setOnClickListener {
                Toast.makeText(mainActivity, "옵션이 설정되었습니다 $optionCost", Toast.LENGTH_SHORT).show()
                onClickListener.onClicked(optionCost)
                dialog.dismiss()
            }

            dialog.cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

        interface OnDialogClickListener
        {
            fun onClicked(cost: Int)
        }
    }
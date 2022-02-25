package com.example.ediya_kiosk.fragment

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.cost_option_dialog_layout.*
import kotlinx.android.synthetic.main.cost_option_dialog_layout.view.*
import kotlinx.android.synthetic.main.menu_detail_layout.*
import android.os.Bundle as Bundle1

class menu_detail_fragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle1?
    ): View {
        var view = inflater.inflate(R.layout.menu_detail_layout,container,false)

        val menuName = arguments?.getString("name")
        val menuPrice = arguments?.getString("price")
        val menuImg = arguments?.getString("img")

        var nametxt = view.findViewById<TextView>(R.id.menuNameTV)
        var pricetxt = view.findViewById<TextView>(R.id.menuPriceTV)
        var menuimg = view.findViewById<ImageView>(R.id.menuIVforDetail)
        var menucost = view.findViewById<TextView>(R.id.totalCostInDetail)
        var optioncost = 0
        var menuPriceInt = (menuPrice!!.substring(0,4)).toInt()
        var totalcost = menuPriceInt + optioncost


        nametxt.text = menuName
        pricetxt.text = menuPrice
        menucost.text = (totalcost.toString()).plus("원")
        menuimg.setImageResource(menuImg!!.toInt())

        // temp 설정
        var hotBtn = view.findViewById<Button>(R.id.hotBtn)
        hotBtn?.setOnClickListener {
            tempOnClick(hotBtn)
        }

        var iceBtn = view.findViewById<Button>(R.id.iceBtn)
        iceBtn?.setOnClickListener {
            tempOnClick(iceBtn)
        }

        // size 설정
        var tallBtn = view.findViewById<ImageButton>(R.id.sizeBtn1)
        tallBtn.setOnClickListener {
            sizeOnClick(tallBtn)
        }

        var grandeBtn = view.findViewById<ImageButton>(R.id.sizeBtn2)
        grandeBtn.setOnClickListener {
            sizeOnClick(grandeBtn)
        }

        var ventiBtn = view.findViewById<ImageButton>(R.id.sizeBtn3)
        ventiBtn.setOnClickListener {
            sizeOnClick(ventiBtn)
        }

        // 장바구니 담기
        var intoBasketBtn = view.findViewById<Button>(R.id.intoBasketBtn)
        intoBasketBtn.setOnClickListener {
            basketOnClick()
        }

        //option dialog 띄우기
        val goOptionBtn = view.findViewById<Button>(R.id.costOptionBtn)
        goOptionBtn.setOnClickListener {
            val dialog = optionDialog(mainActivity)
            dialog.showDialog(mainActivity)
            dialog.setOnClickListener(object  : optionDialog.OnDialogClickListener {
                override fun onClicked(cost: Int)
                {
                    optioncost = cost
                    totalcost = menuPriceInt + optioncost
                    menucost.text = (totalcost.toString()).plus("원")

                }

            })
                    }

        //메뉴 수량 조절
        val menuPlusBtn = view.findViewById<ImageButton>(R.id.detailPlusBtn_1)
        val menuMinusBtn = view.findViewById<ImageButton>(R.id.detailMinusBtn_1)
        val menuOutputNum = view.findViewById<TextView>(R.id.detailQuantity_1)
        var menuNum = 1

        menuPlusBtn.setOnClickListener {
            menuNum++
            menuOutputNum.text = menuNum.toString()
            if (menuNum <= 2) {
                totalcost += totalcost
            }
            else {
                totalcost += (totalcost/(menuNum-1))
            }
            menucost.text = (totalcost.toString()).plus("원")
        }
        menuMinusBtn.setOnClickListener {
            if (menuNum == 1) {
                !menuMinusBtn.isClickable
            }
            else {
                menuMinusBtn.isClickable
                menuNum--
                menuOutputNum.text = menuNum.toString()
                totalcost -= (totalcost /(menuNum+1))
                menucost.text = (totalcost.toString()).plus("원")

            }
        }

        return view
    }

//private fun TextView.text(toString: String) {
//
//}

fun tempOnClick(btn : Button) {
        btn?.isSelected = btn?.isSelected != true
            if (btn == hotBtn) {
                iceBtn?.isSelected = false
            }
            else {
                hotBtn?.isSelected = false
            }
        }

    fun sizeOnClick(btn : ImageButton) {
        btn?.isSelected = btn?.isSelected != true
            if (btn == sizeBtn1) {
                sizeBtn2?.isSelected = false
                sizeBtn3?.isSelected = false
            }
            else if (btn == sizeBtn2) {
                sizeBtn1.isSelected = false
                sizeBtn3.isSelected = false
            }
            else if (btn == sizeBtn3) {
                sizeBtn1.isSelected = false
                sizeBtn2.isSelected = false
            }
    }

    fun basketOnClick() {
        if (!hotBtn.isSelected and !iceBtn.isSelected) {
            Toast.makeText(mainActivity, "온도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        else if (!sizeBtn1.isSelected and !sizeBtn2.isSelected and !sizeBtn3.isSelected) {
            Toast.makeText(mainActivity, "사이즈를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val dlg: AlertDialog.Builder = AlertDialog.Builder(mainActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setMessage("장바구니에 메뉴가 담겼습니다.") // 메시지
            dlg.setNegativeButton("바로가기") { dialog, which ->
                mainActivity!!.openOtherFragmentforBundle(1,basket_fragment())
            }
            dlg.setPositiveButton("확인") { dialog, which ->
                mainActivity!!.openOtherFragmentforBundle(1,MainFragment())
            }
            dlg.show()
        }
    }

    fun viewOptionDialog(menuName : String?) {
        val builder = AlertDialog.Builder(mainActivity).create()
        val dialogView = layoutInflater.inflate(R.layout.cost_option_dialog_layout, null)
        val dialogText = dialogView.findViewById<TextView>(R.id.menuNameTV)
        val dialogExistBtn = dialogView.findViewById<Button>(R.id.cancelBtn)
        val dialogConfirmBtn = dialogView.findViewById<Button>(R.id.choseBtn)

        // plus Btn
        val plusBtn1 = dialogView.findViewById<ImageButton>(R.id.optionPlusBtn_1)
        val plusBtn2 = dialogView.findViewById<ImageButton>(R.id.optionPlusBtn_2)
        val plusBtn3 = dialogView.findViewById<ImageButton>(R.id.optionPlusBtn_3)
        val plusBtn4 = dialogView.findViewById<ImageButton>(R.id.optionPlusBtn_4)
        val plusBtn5 = dialogView.findViewById<ImageButton>(R.id.optionPlusBtn_5)
        val plusBtnList = listOf<ImageButton>(plusBtn1,plusBtn2,plusBtn3,plusBtn4,plusBtn5)


        // minus Btn
        val minusBtn1 = dialogView.findViewById<ImageButton>(R.id.optionMinusBtn_1)
        val minusBtn2 = dialogView.findViewById<ImageButton>(R.id.optionMinusBtn_2)
        val minusBtn3 = dialogView.findViewById<ImageButton>(R.id.optionMinusBtn_3)
        val minusBtn4 = dialogView.findViewById<ImageButton>(R.id.optionMinusBtn_4)
        val minusBtn5 = dialogView.findViewById<ImageButton>(R.id.optionMinusBtn_5)
        val minusBtnList = listOf<ImageButton>(minusBtn1,minusBtn2,minusBtn3,minusBtn4,minusBtn5)


        //output txt
        val outputNum1 = dialogView.findViewById<TextView>(R.id.optionQuantity_1)
        val outputNum2 = dialogView.findViewById<TextView>(R.id.optionQuantity_2)
        val outputNum3 = dialogView.findViewById<TextView>(R.id.optionQuantity_3)
        val outputNum4 = dialogView.findViewById<TextView>(R.id.optionQuantity_4)
        val outputNum5 = dialogView.findViewById<TextView>(R.id.optionQuantity_5)
        val outputNumList = listOf<TextView>(outputNum1,outputNum2,outputNum3,outputNum4,outputNum5)

        // number
        var number1 = 0
        var number2 = 0
        var number3 = 0
        var number4 = 0
        var number5 = 0
        val numberList = mutableListOf<Int>(number1,number2,number3,number4,number5)

        var optionCost = 0


        builder.setView(dialogView)
        dialogText.text = menuName

        for (i in 0..4) {
            plusBtnList[i]?.setOnClickListener {
                if (numberList[i] == 9) {
                    !plusBtnList[i].isClickable
                }
                else {
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
                }
                else {
                    minusBtnList[i].isClickable
                    numberList[i]--
                    optionCost -= 500
                    outputNumList[i].setText(numberList[i].toString())
                }
            }
        }


        dialogExistBtn.setOnClickListener {
            builder.dismiss()
        }
        dialogConfirmBtn.setOnClickListener {
            var dialogBundle = Bundle1()
            dialogBundle.putInt("optionCost",optionCost)



            Toast.makeText(mainActivity, "옵션이 설정되었습니다", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }
        builder.setCancelable(true)
        builder.show()

    }
}
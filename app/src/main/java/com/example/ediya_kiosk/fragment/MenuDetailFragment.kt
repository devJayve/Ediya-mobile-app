package com.example.ediya_kiosk.fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.*
import com.example.ediya_kiosk.activity.MainActivity
import com.example.ediya_kiosk.dialog.optionDialog
import kotlinx.android.synthetic.main.menu_detail_layout.*
import android.os.Bundle as Bundle1

class MenuDetailFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle1?
    ): View {
        val view = inflater.inflate(R.layout.menu_detail_layout, container, false)

        val menuName = arguments?.getString("name")
        val menuPrice = arguments?.getString("price")
        val menuImg = arguments?.getString("img")

        val nameTxt = view.findViewById<TextView>(R.id.menuNameTV)
        val priceTxt = view.findViewById<TextView>(R.id.menuPriceTV)
        val imgIV = view.findViewById<ImageView>(R.id.menuIVforDetail)
        val menuCost = view.findViewById<TextView>(R.id.totalCostInDetail)

        // button
        val hotBtn = view.findViewById<Button>(R.id.hotBtn)
        val iceBtn = view.findViewById<Button>(R.id.iceBtn)
        val tallBtn = view.findViewById<ImageButton>(R.id.sizeBtn1)
        val grandeBtn = view.findViewById<ImageButton>(R.id.sizeBtn2)
        val ventiBtn = view.findViewById<ImageButton>(R.id.sizeBtn3)
        val menuPlusBtn = view.findViewById<ImageButton>(R.id.detailPlusBtn_1)
        val menuMinusBtn = view.findViewById<ImageButton>(R.id.detailMinusBtn_1)
        val menuOutputNum = view.findViewById<TextView>(R.id.detailQuantity_1)
        val goOptionBtn = view.findViewById<Button>(R.id.costOptionBtn)
        val intoBasketBtn = view.findViewById<Button>(R.id.intoBasketBtn)

        var optionCost = 0
        var menuNum = 1
        val menuPriceInt = (menuPrice!!.substring(0, 4)).toInt()
        var totalCost = menuPriceInt + optionCost


        nameTxt.text = menuName
        priceTxt.text = menuPrice
        menuCost.text = totalCost.toString()
        imgIV.setImageResource(menuImg!!.toInt())

        // temp 설정
        hotBtn?.setOnClickListener {
            tempOnClick(hotBtn)
        }

        iceBtn?.setOnClickListener {
            tempOnClick(iceBtn)
        }

        // size 설정
        tallBtn.setOnClickListener {
            sizeOnClick(tallBtn)
        }

        grandeBtn.setOnClickListener {
            sizeOnClick(grandeBtn)
        }

        ventiBtn.setOnClickListener {
            sizeOnClick(ventiBtn)
        }

        //option dialog 띄우기
        goOptionBtn.setOnClickListener {
            val dialog = optionDialog(mainActivity,1)
            dialog.showDialog(mainActivity)
            dialog.setOnClickListener(object : optionDialog.OnDialogClickListener {
                override fun onClicked(cost: Int) {
                    optionCost = cost
                    totalCost = menuPriceInt + optionCost
                    menuCost.text = totalCost.toString()
                }
            })
        }

        // 장바구니 담기
        var menuTemp : String
        var menuSize : String

        intoBasketBtn.setOnClickListener {
            when {
                !hotBtn.isSelected and !iceBtn.isSelected -> {
                    Toast.makeText(mainActivity, R.string.warning_temp, Toast.LENGTH_SHORT).show()
                }
                !sizeBtn1.isSelected and !sizeBtn2.isSelected and !sizeBtn3.isSelected -> {
                    Toast.makeText(mainActivity,  R.string.warning_size, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    menuTemp = when{
                        hotBtn.isSelected -> "HOT"
                        else -> "ICE"
                    }

                    menuSize = when {
                        sizeBtn1.isSelected -> "Tall"
                        sizeBtn2.isSelected -> "Grande"
                        else -> "Venti"
                    }

                    mainActivity.passBasketData(arrayListOf(
                        menuName.toString(), // 메뉴 이름
                        menuNum.toString(), // 메뉴 개수
                        menuTemp, // 메뉴 온도
                        menuSize, // 메뉴 사이즈
                        menuPriceInt.toString(), // 단품 가격
                        menuImg, // 이미지
                        optionCost.toString(), // 옵션 가격
                        totalCost.toString()  // 메뉴 총 가격
                    ))
                    mainActivity.updateNotification()
                    Toast.makeText(mainActivity, "장바구니에 메뉴를 담았습니다.", Toast.LENGTH_SHORT).show()
                    intoBasketDialog()
                }
            }
        }

        //메뉴 수량 조절
        menuPlusBtn.setOnClickListener {
            menuNum++
            menuOutputNum.text = menuNum.toString()
            totalCost += if (menuNum <= 2) {
                totalCost
            } else {
                (totalCost / (menuNum - 1))
            }
            menuCost.text = totalCost.toString()
        }

        menuMinusBtn.setOnClickListener {
            if (menuNum == 1) {
                !menuMinusBtn.isClickable
            } else {
                menuMinusBtn.isClickable
                menuNum--
                menuOutputNum.text = menuNum.toString()
                totalCost -= (totalCost / (menuNum + 1))
                menuCost.text = totalCost.toString()
            }
        }

        // 결제창 전환
        val paymentBtn = view.findViewById<Button>(R.id.goPaymentBtn)
        paymentBtn.setOnClickListener {
            mainActivity.loadFrag(2)
        }

        //뒤로가기
        val backBtn = view.findViewById<ImageButton>(R.id.backToMainBtn)
        backBtn.setOnClickListener {
            mainActivity.loadFrag(0)
        }

        return view
    }

    private fun tempOnClick(btn: Button) {
        btn.isSelected = btn.isSelected != true
        if (btn == hotBtn) {
            iceBtn?.isSelected = false
        } else {
            hotBtn?.isSelected = false
        }
    }

    private fun sizeOnClick(btn: ImageButton) {
        btn.isSelected = btn.isSelected != true
        when (btn) {
            sizeBtn1 -> {
                sizeBtn2?.isSelected = false
                sizeBtn3?.isSelected = false
            }
            sizeBtn2 -> {
                sizeBtn1.isSelected = false
                sizeBtn3.isSelected = false
            }
            sizeBtn3 -> {
                sizeBtn1.isSelected = false
                sizeBtn2.isSelected = false
            }
        }
    }

    private fun intoBasketDialog() {
        Log.d("Message", "dialog open")
        val dlg: AlertDialog.Builder = AlertDialog.Builder(mainActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        dlg.setMessage("장바구니에 메뉴가 담겼습니다.") // 메시지
        dlg.setCancelable(true)
        dlg.setNegativeButton("바로가기") { _, _ ->
            mainActivity.loadFrag(1)
        }
        dlg.setPositiveButton("확인") { _, _ ->
            mainActivity.openOtherFragmentforBundle(1, MainFragment())
        }
        dlg.show()
    }
}
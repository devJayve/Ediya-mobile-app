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
import kotlinx.android.synthetic.main.menu_detail_layout.*
import android.os.Bundle as Bundle1

class menu_detail_fragment : Fragment() {

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
        var view = inflater.inflate(R.layout.menu_detail_layout, container, false)

        val menuName = arguments?.getString("name")
        val menuPrice = arguments?.getString("price")
        val menuImg = arguments?.getString("img")

        var nametxt = view.findViewById<TextView>(R.id.menuNameTV)
        var pricetxt = view.findViewById<TextView>(R.id.menuPriceTV)
        var menuimg = view.findViewById<ImageView>(R.id.menuIVforDetail)
        var menucost = view.findViewById<TextView>(R.id.totalCostInDetail)
        var optioncost = 0
        var menuPriceInt = (menuPrice!!.substring(0, 4)).toInt()
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

        //option dialog 띄우기
        val goOptionBtn = view.findViewById<Button>(R.id.costOptionBtn)
        goOptionBtn.setOnClickListener {
            val dialog = optionDialog(mainActivity)
            dialog.showDialog(mainActivity)
            dialog.setOnClickListener(object : optionDialog.OnDialogClickListener {
                override fun onClicked(cost: Int) {
                    optioncost = cost
                    totalcost = menuPriceInt + optioncost
                    menucost.text = (totalcost.toString()).plus("원")
                }
            })
        }

        // 장바구니 담기
        var menuTemp : String
        var menuSize : String

        var intoBasketBtn = view.findViewById<Button>(R.id.intoBasketBtn)
        intoBasketBtn.setOnClickListener {
            if (!hotBtn.isSelected and !iceBtn.isSelected) {
                Toast.makeText(mainActivity, "온도를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!sizeBtn1.isSelected and !sizeBtn2.isSelected and !sizeBtn3.isSelected) {
                Toast.makeText(mainActivity, "사이즈를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                menuTemp = when{
                    hotBtn.isSelected -> "HOT"
                    else -> "ICE"
                }

                menuSize = when {
                    sizeBtn1.isSelected -> "Tall"
                    sizeBtn2.isSelected -> "Grande"
                    else -> "Venti"
                }

                mainActivity!!.passBasketData(menuName.toString(),menuTemp,menuSize,menuPriceInt,totalcost,menuImg)
                mainActivity!!.updateNotification()
                Toast.makeText(mainActivity, "장바구니에 메뉴를 담았습니다.", Toast.LENGTH_SHORT).show()
                intoBasketDialog()
            }
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
            } else {
                totalcost += (totalcost / (menuNum - 1))
            }
            menucost.text = (totalcost.toString()).plus("원")
        }

        menuMinusBtn.setOnClickListener {
            if (menuNum == 1) {
                !menuMinusBtn.isClickable
            } else {
                menuMinusBtn.isClickable
                menuNum--
                menuOutputNum.text = menuNum.toString()
                totalcost -= (totalcost / (menuNum + 1))
                menucost.text = (totalcost.toString()).plus("원")

            }
        }

        //뒤로가기
        val backBtn = view.findViewById<ImageButton>(R.id.backToMainBtn)
        backBtn.setOnClickListener {
            mainActivity!!.openOtherFragmentforBundle(5, this)
        }

        return view
    }


    fun tempOnClick(btn: Button) {
        btn?.isSelected = btn?.isSelected != true
        if (btn == hotBtn) {
            iceBtn?.isSelected = false
        } else {
            hotBtn?.isSelected = false
        }
    }

    fun sizeOnClick(btn: ImageButton) {
        btn?.isSelected = btn?.isSelected != true
        if (btn == sizeBtn1) {
            sizeBtn2?.isSelected = false
            sizeBtn3?.isSelected = false
        } else if (btn == sizeBtn2) {
            sizeBtn1.isSelected = false
            sizeBtn3.isSelected = false
        } else if (btn == sizeBtn3) {
            sizeBtn1.isSelected = false
            sizeBtn2.isSelected = false
        }
    }

    fun intoBasketDialog() {
        Log.d("Message", "dialog open")
        val dlg: AlertDialog.Builder = AlertDialog.Builder(mainActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        dlg.setMessage("장바구니에 메뉴가 담겼습니다.") // 메시지
        dlg.setCancelable(true)
        dlg.setNegativeButton("바로가기") { dialog, which ->
            mainActivity!!.loadBasketFrag()
        }
        dlg.setPositiveButton("확인") { dialog, which ->
            mainActivity!!.openOtherFragmentforBundle(1, MainFragment())
        }
        dlg.show()
    }
}
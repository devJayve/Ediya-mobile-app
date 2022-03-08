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

        var totalPrice = arguments?.getInt("totalPrice")

        val ediyaPayBtn = view.findViewById<Button>(R.id.ediyaPayBtn)
        val otherPayBtn = view.findViewById<Button>(R.id.otherPayBtn)
        var orderBtn = view.findViewById<Button>(R.id.paymentBtnInPayment)
        var phoneEditText = view.findViewById<EditText>(R.id.phoneNumberET)
        var phoneCheckBtn = view.findViewById<Button>(R.id.phoneCheckBtn)
        val payContainer = view.findViewById<HorizontalScrollView>(R.id.ediyaPayContainer)
        val otherPayContainer = view.findViewById<RadioGroup>(R.id.otherPayContainer)

        payContainer.visibility = View.GONE
        otherPayContainer.visibility = View.GONE

        //phone spinner
        var mobileList = resources.getStringArray(R.array.spinner_mobile)
        var mobileSpinner = view.findViewById<Spinner>(R.id.mobileSpinner)
        mobileSpinner?.adapter = ArrayAdapter(mainActivity,
            android.R.layout.simple_spinner_item,mobileList) as SpinnerAdapter
        mobileSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var doamain = mobileSpinner.selectedItem.toString()
            }
        }

        //phone
        phoneEditText?.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        phoneCheckBtn.setOnClickListener {
            if (phoneEditText.length() == 0) {
                loadAlertDialog("전화번호를 입력해주세요.")
            }
            else if(phoneEditText.length() < 11) {
                loadAlertDialog("전화번호 11자리를 입력해주세요.")
            }
            else {
                loadAlertDialog("전화번호가 확인되었습니다.")
                !phoneCheckBtn.isClickable
            }
        }

        //coupon
        val couponBtn = view.findViewById<Button>(R.id.showCouponBtn)
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
        val backBtn = view.findViewById<ImageButton>(R.id.backToMainBtn)
        backBtn.setOnClickListener {
            val backDialog = AlertDialog.Builder(mainActivity)
            backDialog.setMessage("결제를 취소하시겠습니까 ?")
            backDialog.setPositiveButton("확인",null)
            backDialog.setNegativeButton("취소",null)
            backDialog.show()
        }


        //return Main
        orderBtn.setOnClickListener {
            Log.d("Message","orderBtn click")
            mainActivity!!.loadFrag(3)
            Toast.makeText(mainActivity,"주문이 완료되었습니다.",Toast.LENGTH_SHORT).show()
        }

        ediyaPayBtn.setOnClickListener {
            setPayContent(ediyaPayBtn)
        }

        otherPayBtn.setOnClickListener {
            setPayContent(otherPayBtn)
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

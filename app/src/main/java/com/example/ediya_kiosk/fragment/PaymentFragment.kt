package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
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
        var orderBtn = view.findViewById<Button>(R.id.paymentBtn)
        val payContainer = view.findViewById<HorizontalScrollView>(R.id.ediyaPayContainer)
        val otherPayContainer = view.findViewById<RadioGroup>(R.id.otherPayContainer)

        payContainer.visibility = View.GONE
        otherPayContainer.visibility = View.GONE

        ediyaPayBtn.setOnClickListener {
            setPayContent(ediyaPayBtn)
        }

        otherPayBtn.setOnClickListener {
            setPayContent(otherPayBtn)
        }

        orderBtn.setOnClickListener {
            payTotalCost()
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

    fun payTotalCost() {

    }
}

package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
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
        val payContainer = view.findViewById<HorizontalScrollView>(R.id.ediyaPayContainer)
        val otherPayContainer = view.findViewById<HorizontalScrollView>(R.id.otherPayContainer)

        ediyaPayBtn.setOnClickListener {
            setPayContent(ediyaPayBtn,payContainer)
        }

        otherPayBtn.setOnClickListener {
            setPayContent(otherPayBtn,otherPayContainer)
        }


        return view
    }

    fun setPayContent (btn : Button, container : HorizontalScrollView) {
        val layoutInflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        btn?.isSelected = true
        if (btn == ediyaPayBtn) {
            otherPayBtn?.isSelected = false
            var payContainView = layoutInflater.inflate(R.layout.payment_ediyapay_layout, null)
            container.addView(payContainView)
        } else {
            ediyaPayBtn?.isSelected = false
            var payContainView = layoutInflater.inflate(R.layout.payment_ediyapay_layout, null)
            container.addView(payContainView)
        }



    }
}

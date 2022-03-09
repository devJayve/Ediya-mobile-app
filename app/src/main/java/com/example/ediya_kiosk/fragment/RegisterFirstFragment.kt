package com.example.ediya_kiosk.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.R

class RegisterFirstFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity
    private var isAllSelected = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.register_layout_1, container, false)
        val termsAllAgreeBtn = view.findViewById<RadioButton>(R.id.termsAllAgreeBtn)
        val termsAgreeBtn1 = view.findViewById<RadioButton>(R.id.termsAgreeBtn_1)
        val termsAgreeBtn2 = view.findViewById<RadioButton>(R.id.termsAgreeBtn_2)
        val termsAgreeBtn3 = view.findViewById<RadioButton>(R.id.termsAgreeBtn_3)
        val nextPageBtn = view.findViewById<Button>(R.id.nextStepBtn1)
        val backPageBtn = view.findViewById<ImageButton>(R.id.backBtnInRegister1)

        termsAllAgreeBtn.setOnClickListener {
            isAllSelected = if (isAllSelected) {
                termsAgreeBtn1.isSelected
                termsAgreeBtn2.isSelected
                termsAgreeBtn3.isSelected
                true
            } else {
                termsAgreeBtn1.isSelected
                termsAgreeBtn2.isSelected
                termsAgreeBtn3.isSelected
                false
            }
        }

        nextPageBtn.setOnClickListener {
            if (!termsAgreeBtn1.isChecked || !termsAgreeBtn2.isChecked) {
                Toast.makeText(loginActivity,"약관에 동의해주세요.",Toast.LENGTH_SHORT).show()
            }
            else {
                loginActivity!!.register(2)
            }
        }

        // 뒤로 가기
        backPageBtn.setOnClickListener {
            val backDialog = AlertDialog.Builder(loginActivity)
            backDialog.setMessage("회원가입을 종료하시겠습니까?")
            backDialog.setPositiveButton("네", DialogInterface.OnClickListener { dialog, id ->
                loginActivity!!.register(5)
            })
            backDialog.setNegativeButton("계속 할래요", null)
            backDialog.show()
        }


        return view
    }
}

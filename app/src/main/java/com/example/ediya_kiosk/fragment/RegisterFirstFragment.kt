package com.example.ediya_kiosk.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.R
import kotlinx.android.synthetic.main.register_layout_1.*


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
        var termsAllAgreeBtn = view.findViewById<CheckBox>(R.id.termsAllAgree_Btn)
        var termsAgreeBtn1 = view.findViewById<CheckBox>(R.id.termsAgreeBtn_1)
        var termsAgreeBtn2 = view.findViewById<CheckBox>(R.id.termsAgreeBtn_2)
        var termsAgreeBtn3 = view.findViewById<CheckBox>(R.id.termsAgreeBtn_3)
        val nextPageBtn = view.findViewById<Button>(R.id.nextStepBtn1)
        val backPageBtn = view.findViewById<ImageButton>(R.id.backBtnInRegister1)


        nextPageBtn.setOnClickListener {
            if (!termsAgreeBtn1.isChecked || !termsAgreeBtn2.isChecked) {
                Toast.makeText(loginActivity,"약관에 동의해주세요.",Toast.LENGTH_SHORT).show()
            }
            else {
                loginActivity!!.register(2)
            }
        }

        //약관 동의
        termsAllAgreeBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked){
                true -> {
                    Log.d("TAG","checked")
                    if (termsAgreeBtn1.isChecked || termsAgreeBtn2.isChecked || termsAgreeBtn3.isChecked) {
                        Log.d("TAG","check1")
                        termsAgreeBtn1.isChecked = true
                        termsAgreeBtn2.isChecked = true
                        termsAgreeBtn3.isChecked = true
                    } else if (!termsAgreeBtn1.isChecked && !termsAgreeBtn2.isChecked && !termsAgreeBtn3.isChecked){
                        termsAgreeBtn1.isChecked = true
                        termsAgreeBtn2.isChecked = true
                        termsAgreeBtn3.isChecked = true
                    }
                }
                false -> {
                    if (!termsAgreeBtn1.isChecked || !termsAgreeBtn2.isChecked || !termsAgreeBtn3.isChecked) {
                        termsAgreeBtn1.isChecked = false
                        termsAgreeBtn2.isChecked = false
                        termsAgreeBtn3.isChecked = false
                    } else if (termsAgreeBtn1.isChecked && termsAgreeBtn2.isChecked && termsAgreeBtn3.isChecked){
                        termsAgreeBtn1.isChecked = false
                        termsAgreeBtn2.isChecked = false
                        termsAgreeBtn3.isChecked = false
                    }
                }
            }
        }


        // 뒤로 가기
        backPageBtn.setOnClickListener {
            val backDialog = AlertDialog.Builder(loginActivity)
            backDialog.setMessage("회원가입을 종료하시겠습니까?")
            backDialog.setPositiveButton("네", DialogInterface.OnClickListener { dialog, id ->
                loginActivity!!.register(5)
            })
            backDialog.setNegativeButton("계속 할래요", null).show()
        }


        return view
    }
}

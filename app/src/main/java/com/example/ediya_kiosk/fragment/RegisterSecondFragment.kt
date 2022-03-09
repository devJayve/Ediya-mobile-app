package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.R
import kotlinx.android.synthetic.main.register_layout_2.*
import java.util.regex.Pattern

class RegisterSecondFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity
    private var mobile = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.register_layout_2, container, false)

        val nameInput = view.findViewById<EditText>(R.id.nameInputET).text.toString()
        val frontBirthInput = view.findViewById<EditText>(R.id.FrontBirthInputET).text.toString()
        val backBirthInput = view.findViewById<EditText>(R.id.BackBirthInputET).text.toString()
        val phoneNumInput = view.findViewById<EditText>(R.id.phoneNumberET).text.toString()

        // radio Btn Setting
        val mobileRadioBtn1 = view.findViewById<RadioButton>(R.id.mobileSktBtn)
        val mobileRadioBtn2 = view.findViewById<RadioButton>(R.id.mobileKtBtn)
        val mobileRadioBtn3 = view.findViewById<RadioButton>(R.id.mobileLGBtn)
        val mobileRadioBtn4 = view.findViewById<RadioButton>(R.id.mobileCheapBtn)
        val mobileRadioGroup = view.findViewById<RadioGroup>(R.id.mobileRadioGroup)
        mobileRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mobileSktBtn -> mobile = "SKT"
                R.id.mobileKtBtn -> mobile = "KT"
                R.id.mobileLGBtn -> mobile = "LG"
                R.id.mobileCheapBtn -> mobile = "Cheap"
            }
        }

        // 예외처리
        val nextPageBtn = view.findViewById<Button>(R.id.nextStepBtn2)
        nextPageBtn.setOnClickListener {
            if (nameInput.isEmpty()) {
                Toast.makeText(loginActivity, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if ((frontBirthInput.isEmpty() || backBirthInput.isEmpty()) && frontBirthInput.count() != 6) {
                Toast.makeText(loginActivity, "주민등록번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!mobileRadioBtn1.isChecked && !mobileRadioBtn2.isChecked && !mobileRadioBtn3.isChecked && !mobileRadioBtn4.isChecked ) {
                Toast.makeText(loginActivity, "통신사를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else if (phoneNumInput.isEmpty()) {
                Toast.makeText(loginActivity, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!Pattern.matches("^01(?:0|1|[6-9]) - (?:\\d{3}|\\d{4}) - \\d{4}$", phoneNumInput)){
                Toast.makeText(loginActivity, "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {
                loginActivity!!.register(3)
            }
        }

        return view
    }
}
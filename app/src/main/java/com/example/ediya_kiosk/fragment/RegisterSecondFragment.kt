package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
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
    private var mobile = 0

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

        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()
        editor.putBoolean("step2",true)
        editor.apply()

        val backPageBtn = view.findViewById<ImageButton>(R.id.backBtnInRegister2)
        val nextPageBtn = view.findViewById<Button>(R.id.nextStepBtn2)

        // radio Btn Setting
        val mobileRadioGroup = view.findViewById<RadioGroup>(R.id.mobileRadioGroup)
        mobileRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.mobileSktBtn -> mobile = 1
                R.id.mobileKtBtn -> mobile = 2
                R.id.mobileLGBtn -> mobile = 3
                R.id.mobileCheapBtn -> mobile = 4
            }
        }

        // 다음 페이지로 이동
        nextPageBtn.setOnClickListener {
            moveToNextPage()
        }

        //뒤로 가기
        backPageBtn.setOnClickListener{
            moveToBackPage()
        }
        return view
    }

    private fun moveToNextPage() {
        if (TextUtils.isEmpty(nameInputET.text)) {
            Toast.makeText(loginActivity, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if (!Pattern.matches("^[가-힣]*\$",nameInputET.text)) {
                Toast.makeText(loginActivity, "한글로 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if ((TextUtils.isEmpty(FrontBirthInputET.text) || TextUtils.isEmpty(BackBirthInputET.text)) || FrontBirthInputET.text.count() != 6) {
            Toast.makeText(loginActivity, "주민등록번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
        } else if (!mobileSktBtn.isChecked && !mobileKtBtn.isChecked && !mobileLGBtn.isChecked && !mobileCheapBtn.isChecked ) {
            Toast.makeText(loginActivity, "통신사를 선택해주세요.", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(phoneNumberET.text)) {
            Toast.makeText(loginActivity, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else if (!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phoneNumberET.text)){
            Toast.makeText(loginActivity, "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
        } else { saveMemoryInPref(arrayListOf(nameInputET.text.toString(),FrontBirthInputET.text.toString(),BackBirthInputET.text.toString(),mobile.toString(),phoneNumberET.text.toString()))
            loginActivity!!.register(3)
        }
    }

    private fun saveMemoryInPref(memoryList: ArrayList<String>) {
        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()
        editor.putBoolean("step2",true)
        editor.putString("name",memoryList[0])
        editor.putInt("frontBirth",memoryList[1].toInt())
        editor.putInt("backBirth",memoryList[2].toInt())
        editor.putInt("mobileIndex",memoryList[3].toInt())
        editor.putInt("phoneNum",memoryList[4].toInt())
        editor.apply()
    }

    private fun moveToBackPage() {
            val pref = loginActivity.getPreferences(0)
            val editor = pref.edit()
            editor.remove("step2")
            editor.remove("frontBirth")
            editor.remove("backBirth")
            editor.remove("mobileIndex")
            editor.remove("phoneNum")
            editor.apply()
        loginActivity!!.register(1)
    }
}
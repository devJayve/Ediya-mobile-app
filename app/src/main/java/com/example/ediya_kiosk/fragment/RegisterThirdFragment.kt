package com.example.ediya_kiosk.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.LoginActivity
import com.example.ediya_kiosk.R
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import kotlinx.android.synthetic.main.register_layout_2.*
import kotlinx.android.synthetic.main.register_layout_3.*
import java.util.regex.Pattern

class RegisterThirdFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity
    private var isOverlapCheck = false
    private var isDomainSelected = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.register_layout_3, container, false)

        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()

        editor.putBoolean("step3", true)
        editor.apply()
        Log.d("TAG","step3 is ${pref.getBoolean("step3",false)}")

        //db
        val db = Database(loginActivity, "ediya.db", null, 1)
        val readableDb = db.readableDatabase
        val writableDb = db.writableDatabase

        val idInRegisterET = view.findViewById<EditText>(R.id.idInRegisterET)
        val idInRegister = idInRegisterET.text.toString()
        val idOverlapBtn = view.findViewById<Button>(R.id.idOverlapCheckBtn)
        val nextPageBtn = view.findViewById<Button>(R.id.nextStepBtn3)
        val backPageBtn = view.findViewById<ImageButton>(R.id.backBtnInRegister3)

        // domain Spinner
        var domainList = resources.getStringArray(R.array.domain_array)
        var domainSpinner = view.findViewById<Spinner>(R.id.domainSpinner)
        domainSpinner?.adapter = ArrayAdapter(
            loginActivity,
            android.R.layout.simple_spinner_item, domainList
        )
        domainSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var doamain = domainSpinner.selectedItem.toString()
                isDomainSelected = true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // 아이디 중복 체크
        idOverlapBtn.setOnClickListener {
            val dbControl = DatabaseControl()
            val idData = dbControl.readData(
                readableDb,
                "account",
                arrayListOf("id"),
                arrayListOf("id"),
                arrayListOf("$idInRegister")
            )
            isOverlapCheck = if (idData.size != 0) {
                showToastWarning("중복된 아이디 입니다.")
                false
            } else {
                showToastWarning("사용 가능한 아이디입니다.")
                !idOverlapBtn.isEnabled
                true
            }
        }

        idInRegisterET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                isOverlapCheck = false
                idOverlapBtn.isEnabled
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isOverlapCheck = false
                idOverlapBtn.isEnabled
            }
        })

        // 회원가입 완료
        nextPageBtn.setOnClickListener {
            finishRegister()
        }

        //뒤로 가기
        backPageBtn.setOnClickListener {
            moveToBackPage()
        }

        return view
    }

    private fun showToastWarning(txt: String) {
        Toast.makeText(loginActivity, "$txt", Toast.LENGTH_SHORT).show()
    }

    private fun finishRegister() {
        exceptionHandling()
        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()
        editor.remove("step3")
        editor.remove("step2")
        editor.remove("frontBirth")
        editor.remove("backBirth")
        editor.remove("mobileIndex")
        editor.remove("phoneNum")
        editor.apply()

        loginActivity!!.register(4)
    }


    private fun exceptionHandling() {
        var password = view?.findViewById<EditText>(R.id.pwInRegisterET)?.text.toString()
        var rePassword = view?.findViewById<EditText>(R.id.pwInRegisterET)?.text.toString()

        if (TextUtils.isEmpty(idInRegisterET.text)) {
            showToastWarning("아이디를 입력해주세요.")
        } else if (!isOverlapCheck) {
            showToastWarning("아이디가 중복 체크되지 않았습니다.")
        } else if (TextUtils.isEmpty(pwInRegisterET.text)) {
            showToastWarning("비밀번호를 확인해주세요.")
        } else if (!Pattern.matches(
                "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$",pwInRegisterET.text)
        ) {
            showToastWarning("비밀번호 형식이 올바르지 않습니다.")
        } else if (password != rePassword) {
            Log.d("TAG","pw : ${pwInRegisterET.text}, rePw : ${rePwInRegisterET.text}")
            showToastWarning("비밀번호가 서로 일치하지 않습니다.")
        } else if (TextUtils.isEmpty(emailET.text)) {
            showToastWarning("이메일 주소를 입력해주세요.")
        } else if (!isDomainSelected) {
            showToastWarning("도메인 주소를 선택해주세요.")
        } else {
            saveInLocalDb(idInRegisterET.text.toString(),pwInRegisterET.text.toString(),emailET.text.toString())
        }
    }


    private fun moveToBackPage() {
        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()
        editor.remove("step3")
        editor.apply()
        loginActivity!!.register(2)
    }

    private fun saveInLocalDb(id: String,pw: String,email: String) {
        val db = Database(loginActivity, "ediya.db", null, 1)
        val readableDb = db.readableDatabase
        val writableDb = db.writableDatabase
        var domainSpinner = view?.findViewById<Spinner>(R.id.domainSpinner)

        val dbControl = DatabaseControl()

        val accountColumnList = arrayListOf("id","password","email","certification_num","phone_num")
        val basketColumnList = arrayListOf("id")
        val basketOrderColumnList = arrayListOf("id")
        val interfaceColumnList = arrayListOf("id","isMode","isLanguage")

        val pref = loginActivity.getPreferences(0)

        var name = pref.getString("name","")
        var certificationNum = pref.getInt("frontBirth",0).plus(pref.getInt("backBirth",0))
        var phoneNum = pref.getInt("phoneNum",0)
        var domain = domainSpinner?.selectedItem.toString()
        var totalEmail = "$email@$domain"
        var accountValueList = arrayListOf(id,pw,totalEmail,certificationNum.toString(),phoneNum.toString())

        dbControl.createData(writableDb,"account",accountColumnList,accountValueList)
        dbControl.createData(writableDb,"basket", arrayListOf("id"), arrayListOf(id))
        dbControl.createData(writableDb,"basketOrder",arrayListOf("id"), arrayListOf(id))
        dbControl.createData(writableDb, "interface",arrayListOf("id"), arrayListOf(id))
    }
}
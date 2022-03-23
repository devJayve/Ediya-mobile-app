package com.example.ediya_kiosk.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.*
import com.example.ediya_kiosk.activity.LoginActivity
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.register_layout_3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegisterThirdFragment : Fragment() {
    private lateinit var loginActivity: LoginActivity
    private val myTag = "TAG"


    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginActivity = activity as LoginActivity
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.register_layout_3, container, false)

        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()

        editor.putBoolean("step3", true)
        editor.apply()
        Log.d("TAG","step3 is ${pref.getBoolean("step3",false)}")

        //db
        val db = Database(loginActivity, "ediya.db", null, 1)
        val readableDb = db.readableDatabase
        val writableDb = db.writableDatabase

        //state
        var isOverlapCheck = false
        var isPwStated = false
        var isRePwStated = false
        var isIdStated = false
        var isEmailStated = false

        //editText
        val idET = view.findViewById<EditText>(R.id.idInRegisterET)
        val pwET = view.findViewById<EditText>(R.id.pwInRegisterET)
        val rePwET = view.findViewById<EditText>(R.id.rePwInRegisterET)
        val emailET = view.findViewById<EditText>(R.id.emailET)

        //Button
        val idOverlapBtn = view.findViewById<Button>(R.id.idOverlapCheckBtn)
        val nextPageBtn = view.findViewById<Button>(R.id.nextStepBtn3)
        val backPageBtn = view.findViewById<ImageButton>(R.id.backBtnInRegister3)

        //inputLayout
        val idInputLayout = view.findViewById<TextInputLayout>(R.id.idInputLayout)
        val rePwInputLayout = view.findViewById<TextInputLayout>(R.id.rePwInputLayout)
        val pwInputLayout = view.findViewById<TextInputLayout>(R.id.pwInputLayout)
        val emailInputLayout = view.findViewById<TextInputLayout>(R.id.emailInputLayout)

        // domain Spinner
        val domainList = resources.getStringArray(R.array.domain_array)
        val domainSpinner = view.findViewById<Spinner>(R.id.domainSpinner)
        domainSpinner?.adapter = ArrayAdapter(
            loginActivity,
            android.R.layout.simple_spinner_item, domainList)
        domainSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var doamain = domainSpinner.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //아이디 예외처리
        idET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (idET.length() < 4) {
                    isIdStated = false
                    isOverlapCheck = false
                    idInputLayout.error = "4자 이상 입력해주세요."
                    idOverlapBtn.isEnabled = false
                } else if (!Pattern.matches("^[A-Za-z0-9]*$",idET.text)) {
                    idInputLayout.error = "아이디 형식에 부합하지 않습니다."
                    idOverlapBtn.isEnabled = false
                } else {
                    isIdStated = true
                    idET.backgroundTintList = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                    idInputLayout.hintTextColor = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                    idInputLayout.error = null
                    idOverlapBtn.isEnabled = true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        //비밀번호 예외처리
        pwInputLayout.isCounterEnabled = true
        pwET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (pwET.length() < 8) {
                    isPwStated = false
                    pwInputLayout.error = "8자 이상 입력해주세요."
                } else if (!Pattern.matches(
                        "^(?=.*\\d)(?=.*[~`!@#$%^&*()-])(?=.*[a-zA-Z]).{8,20}$",
                        pwInRegisterET.text
                    )) {
                    pwInputLayout.error = "비밀번호 형식이 올바르지 않습니다."
                } else {
                    pwInputLayout.error = null
                    pwET.backgroundTintList = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                    pwInputLayout.hintTextColor = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                    isPwStated = true
                }
            }
            override fun afterTextChanged(p0: Editable?) = Unit
        })

        //비밀번호 재입력 예외처리
        rePwInputLayout.isCounterEnabled = true
        rePwET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (pwET.text.toString() != rePwET.text.toString()) {
                    isRePwStated = false
                    rePwInputLayout.error = "비밀번호가 일치하지 않습니다."
                } else {
                    rePwInputLayout.error = null
                    rePwET.backgroundTintList = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                    rePwInputLayout.hintTextColor = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                    isRePwStated = true
                    Log.d(myTag,"$isRePwStated")
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        //이메일 예외처리
        emailET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (emailET.length() < 1) {
                    isEmailStated = false
                    emailInputLayout.error = "이메일을 입력해주세요."
                }  else {
                    emailInputLayout.error = null
                    emailET.backgroundTintList = ContextCompat.getColorStateList(loginActivity,R.color.gray)
                    emailInputLayout.hintTextColor = ContextCompat.getColorStateList(loginActivity,R.color.gray)
                    isEmailStated = true
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        })

        // 아이디 중복 체크
        idOverlapBtn.setOnClickListener {
            val dbControl = DatabaseControl()
            val idData = dbControl.readData(
                readableDb,
                "account",
                arrayOf("id"),
                arrayListOf("id"),
                arrayOf(idET.text.toString())
            )
            if (idET.length() != 0) {
                if (idData.size != 0) {
                    idInputLayout.error = "중복된 아이디 입니다."
                    isOverlapCheck = false
                } else {
                    val retrofit = RetrofitClient.initRetrofit()

                    val requestOverlapApi = retrofit.create(OverlapApi::class.java)
                    requestOverlapApi.getOverlapId(idET.text.toString()).enqueue(object : Callback<LoginData> {
                        override fun onFailure(call: Call<LoginData>, t: Throwable) {
                            Log.d("Error","$t")
                        }

                        override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                            if (response.body()!!.success) {
                                showToastWarning("사용 가능한 아이디입니다.")
                                idInputLayout.backgroundTintList = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                                idInputLayout.hintTextColor = ContextCompat.getColorStateList(loginActivity,R.color.blue)
                                isIdStated = true
                                isOverlapCheck = true
                            } else {
                                idInputLayout.error = "중복된 아이디 입니다."
                                isOverlapCheck = false
                            }
                        }
                    })
                }
            }
        }

        // 회원가입 완료
        nextPageBtn.setOnClickListener {

            if (isOverlapCheck && isPwStated && isRePwStated && isIdStated && isEmailStated) {
                val id = idET.text.toString()
                val pw = pwET.text.toString()
                val name = pref.getString("name","")
                val contact : String? = pref.getString("phoneNum","")

                saveInLocalDb(id,pw,contact.toString())

                val userInfo = UserInfo(
                    userId = id,
                    userName = name,
                    userPw = pw,
                    userContact = contact,
                )

                postNewUserInServer(userInfo)
                finishRegister()
            }
            else Toast.makeText(loginActivity,"회원 정보를 다시 확인해주세요.",Toast.LENGTH_SHORT).show()
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
        val pref = loginActivity.getPreferences(0)
        val editor = pref.edit()
        editor.remove("step3")
        editor.remove("step2")
        editor.remove("frontBirth")
        editor.remove("backBirth")
        editor.remove("mobileIndex")
        editor.remove("phoneNum")
        editor.remove("name")
        editor.apply()
        loginActivity!!.register(4)
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
        val writableDb = db.writableDatabase
        var domainSpinner = view?.findViewById<Spinner>(R.id.domainSpinner)
        val dbControl = DatabaseControl()

        val accountColumnList = arrayListOf("id","password","email","name","certification_num","phone_num","point")

        val pref = loginActivity.getPreferences(0)
        var name = pref.getString("name","").toString()
        var certificationNum = pref.getInt("frontBirth",0).plus(pref.getInt("backBirth",0))
        var phoneNum = pref.getString("phoneNum","")
        var domain = domainSpinner?.selectedItem.toString()
        var totalEmail = "$email@$domain"
        var accountValueList = arrayListOf(id,pw,totalEmail,name,certificationNum.toString(),phoneNum.toString(),"2000")

        dbControl.createData(writableDb,"account",accountColumnList,accountValueList)
        dbControl.createData(writableDb, "interface",arrayListOf("id","isMode","isLanguage"), arrayListOf(id,"0","0"))
    }

    private fun postNewUserInServer(userData: UserInfo) {
        val retrofit = RetrofitClient.initRetrofit()

        val requestRegisterApi = retrofit.create(RegisterApi::class.java)
        requestRegisterApi.postNewUser(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                }

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                }
            }
        )
    }
}
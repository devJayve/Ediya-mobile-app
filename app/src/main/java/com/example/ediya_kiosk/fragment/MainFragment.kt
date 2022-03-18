package com.example.ediya_kiosk.fragment

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ediya_kiosk.MainActivity
import com.example.ediya_kiosk.OnDayNightStateChanged
import com.example.ediya_kiosk.recycler_view.MainRvAdapter
import com.example.ediya_kiosk.recycler_view.MenuData
import com.example.ediya_kiosk.R
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), OnDayNightStateChanged {

    private lateinit var myadapter: MainRvAdapter
    private lateinit var mainActivity: MainActivity
    private var isFabOpen = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        myadapter = MainRvAdapter()
    }

    // 데이터를 담는 배열
    var menuList = ArrayList<MenuData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        val backBtn = view.findViewById<ImageButton>(R.id.logoutBtn)
        val basketBtn = view.findViewById<FloatingActionButton>(R.id.basketBtn)
        val settingBtn = view.findViewById<FloatingActionButton>(R.id.setting_btn)
        val darkModeBtn = view.findViewById<FloatingActionButton>(R.id.dark_mode_btn)
        val languageBtn = view.findViewById<FloatingActionButton>(R.id.set_language_btn)
        val orderHistoryBtn = view.findViewById<ImageButton>(R.id.historyBtn)


        //아이디 세팅
        var userId = arguments?.getString("userId").toString()
        var userIdTV = view.findViewById<TextView>(R.id.userIdTV)
        userIdTV.text = userId.plus("님")

        // basket 으로 이동
        basketBtn.setOnClickListener {
            mainActivity!!.loadFrag(1)
        }

        myadapter.setItemClickListener(object : MainRvAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                Log.d("Message", "${position}번 리스트 선택")
                var bundle =  Bundle()
                var fragment = MenuDetailFragment()
                bundle.putString("name",menuList[position].menuName)
                bundle.putString("price",menuList[position].menuPrice)
                bundle.putString("img",menuList[position].menuPhotoImg)

                fragment.arguments = bundle
                mainActivity?.openOtherFragmentforBundle(2,fragment)}
        })

        // 설정 버튼
        settingBtn.setOnClickListener {
            toggleFab()
        }

        // 다크모드
        darkModeBtn.setOnClickListener {
            if (AppCompatDelegate.getDefaultNightMode() != MODE_NIGHT_YES) {
                onDayNightApplied(2)
                Toast.makeText(mainActivity, "다크모드 버튼 클릭!", Toast.LENGTH_SHORT).show()
            } else {
                onDayNightApplied(1)
                Toast.makeText(mainActivity, "다크모드 버튼 클릭!", Toast.LENGTH_SHORT).show()
            }
        }


        // 한/영 전환
        languageBtn.setOnClickListener {
            Toast.makeText(mainActivity, "한/영 버튼 클릭!", Toast.LENGTH_SHORT).show()
        }

        // 주문 기록 이동
    orderHistoryBtn.setOnClickListener {
            mainActivity.loadFrag(4)
        }

        // 뒤로 가기
        backBtn.setOnClickListener {
            val backDialog = AlertDialog.Builder(mainActivity)
            backDialog.setMessage("로그아웃 하시겠습니까?")
            backDialog.setPositiveButton("네", DialogInterface.OnClickListener { dialog, id ->
                mainActivity!!.logout()

            })
            backDialog.setNegativeButton("아니요", null).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val res = resources
        var name = res.getStringArray(R.array.coffee_name)
        var price = res.getStringArray(R.array.coffee_price)
        var img = context?.resources?.obtainTypedArray(R.array.coffee_img)

        initRecyclerView(name, price, "COFFEE", img)
        clickCategoryEvent(view)
    }

    fun initRecyclerView(name: Array<String>, price: Array<String>, cateogry: String, img: TypedArray?) {
        this.menuList.clear()
        for (i in name.indices) {
            var imgInt : Int = img?.getResourceId(i,-1)!!.toInt()
            val menuData = MenuData("$cateogry", "${name[i]}", "${price[i]}", "${imgInt}")
            this.menuList.add(menuData)

            //어답터 인스턴스 생성
            //myadapter = MainRvAdapter()

            myadapter.submitList(this.menuList)

            // 리사이클뷰 설정
            menuRecycleView.apply {
                layoutManager =
                    LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)

                //어답터 장착
                adapter = myadapter
                myadapter.notifyDataSetChanged()
            }
        }
    }

    fun clickCategoryEvent(view: View) {
        val res = resources
        var coffeeBtn = view.findViewById<Button>(R.id.coffeeBtn)
        var beverageBtn = view.findViewById<Button>(R.id.beverageBtn)
        var blendingTeaBtn = view.findViewById<Button>(R.id.blendingTeaBtn)
        var adeBtn = view.findViewById<Button>(R.id.adeBtn)
        var shakeBtn = view.findViewById<Button>(R.id.shakeBtn)
        var flatccinoBtn = view.findViewById<Button>(R.id.flatccinoBtn)
        var bubbleMilkTeaBtn = view.findViewById<Button>(R.id.bubbleMilkTeaBtn)
        var bakeryBtn = view.findViewById<Button>(R.id.bakeryBtn)
        var buttonList = listOf<Button>(
            coffeeBtn,
            beverageBtn,
            blendingTeaBtn,
            adeBtn,
            shakeBtn,
            flatccinoBtn,
            bubbleMilkTeaBtn,
            bakeryBtn
        )
        var categoryList = arrayListOf("COFFEE", "베버러지", "블렌딩 티", "에이드", "쉐이크", "플랫치노", "버블 밀크티","베이커리")
        var nameArrayList = listOf(
            R.array.coffee_name,
            R.array.beverage_name,
            R.array.blendingtea_name,
            R.array.ade_name,
            R.array.shake_name,
            R.array.flatccino_name,
            R.array.bubblemilktea_name,
            R.array.bakery_name
        )
        var priceArrayList = listOf(
            R.array.coffee_price,
            R.array.beverage_price,
            R.array.blendingtea_price,
            R.array.ade_price,
            R.array.shake_price,
            R.array.flatccino_price,
            R.array.bubblemilktea_price,
            R.array.bakery_price
        )
        var imageArrayList = listOf(
            R.array.coffee_img,
            R.array.beverage_img,
            R.array.blendingtea_img,
            R.array.ade_img,
            R.array.shake_img,
            R.array.flatccino_img,
            R.array.bubblemilktea_img,
            R.array.bakery_img
        )

        for (index in 0..7) {
            buttonList.get(index).setOnClickListener {
                var name = res.getStringArray(nameArrayList.get(index))
                var price = res.getStringArray(priceArrayList.get(index))
                var photo = context?.resources?.obtainTypedArray(imageArrayList.get(index))
                initRecyclerView(name, price, categoryList[index],photo)
            }
        }
    }

    private fun toggleFab() {
        if (isFabOpen) {
            ObjectAnimator.ofFloat(dark_mode_btn, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(set_language_btn, "translationY", 0f).apply { start() }

        } else {
            ObjectAnimator.ofFloat(dark_mode_btn, "translationY", -200f).apply { start() }
            ObjectAnimator.ofFloat(set_language_btn, "translationY", -400f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }

    override fun onDayNightApplied(state: Int) {
        if(state == OnDayNightStateChanged.DAY){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            mainActivity.updateInterface(0)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            mainActivity.updateInterface(1)
        }
    }
}







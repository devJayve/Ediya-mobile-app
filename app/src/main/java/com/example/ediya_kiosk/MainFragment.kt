package com.example.ediya_kiosk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ediya_kiosk.fragment.basket_fragment
import com.example.ediya_kiosk.fragment.menu_detail_fragment
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_layout.view.*

class MainFragment : Fragment() {

    private lateinit var myadapter: MainRvAdapter
    private lateinit var mainActivity: MainActivity

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
        var view = inflater.inflate(R.layout.main_fragment, container, false)

        var basketBtn = view.findViewById<Button>(R.id.basketBtn)
        basketBtn.setOnClickListener {
            mainActivity!!.openOtherFragment(1)
        }

        myadapter.setItemClickListener(object :MainRvAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                Log.d("Message", "${position}번 리스트 선택")
                var bundle =  Bundle()
                var fragment = menu_detail_fragment()
                bundle.putString("name",menuList[position].menuName)
                bundle.putString("price",menuList[position].menuPrice)

                fragment.arguments = bundle
                mainActivity?.openOtherFragmentforBundle(4,fragment)}
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val res = resources
        var name = res.getStringArray(R.array.coffee_name)
        var price = res.getStringArray(R.array.coffee_price)
        var img = res.getStringArray(R.array.coffee_img)

        initRecyclerView(name, price, "COFFEE", img)
        clickCategoryEvent(view)
    }


    fun initRecyclerView(name: Array<String>, price: Array<String>, cateogry: String, img: Array<Drawable>) {
        this.menuList.clear()
        for (i in name.indices) {
            val menuData = MenuData("$cateogry", "${name[i]}", "${price[i]}", "${img[i]}")
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
                myadapter.notifyDataSetChanged()}
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
            var buttonList = listOf<Button>(
                coffeeBtn,
                beverageBtn,
                blendingTeaBtn,
                adeBtn,
                shakeBtn,
                flatccinoBtn,
                bubbleMilkTeaBtn
            )
            var categoryList = listOf("COFFEE", "베버러지", "블렌딩 티", "에이드", "쉐이크", "플랫치노", "버블 밀크티")
            var nameArrayList = listOf(
                R.array.coffee_name,
                R.array.beverage_name,
                R.array.blendingtea_name,
                R.array.ade_name,
                R.array.shake_name,
                R.array.flatccino_name,
                R.array.bubblemilktea_name
            )
            var priceArrayList = listOf(
                R.array.coffee_price,
                R.array.beverage_price,
                R.array.blendingtea_price,
                R.array.ade_price,
                R.array.shake_price,
                R.array.flatccino_price,
                R.array.bubblemilktea_price
            )

            for (index in 0..4) {
                buttonList.get(index).setOnClickListener {
                    var name = res.getStringArray(nameArrayList.get(index))
                    var price = res.getStringArray(priceArrayList.get(index))
                    var photo = res.getStringArray(R.array.coffee_img)
                    initRecyclerView(name, price, categoryList[index],photo)
                }
            }
        }

    }







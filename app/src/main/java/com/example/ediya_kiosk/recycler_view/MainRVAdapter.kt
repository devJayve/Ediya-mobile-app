package com.example.ediya_kiosk.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ediya_kiosk.activity.MainActivity
import com.example.ediya_kiosk.R

class MainRvAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    private var menuList = ArrayList<MenuData>()
    private var activity: MainActivity? = null


    //뷰홀더 생성 시
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MainViewHolder(view)
    }

    //목록의 개수
    override fun getItemCount(): Int {
        return menuList.size
    }

    //뷰와 뷰 홀더가 묵였을 때
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(this.menuList[position])
        //클릭 리스너 설정
        holder.itemView.setOnClickListener {
            //Toast.makeText(App.instance, "${this.menuList[position].menuName}",Toast.LENGTH_SHORT).show()
            itemClickListner.onClick(it,position)
        }
    }

    fun submitList(menuList: ArrayList<MenuData>) {
        this.menuList = menuList
    }

    interface ItemClickListener{
        fun onClick(view: View,position: Int)
    }
    //를릭 리스너
    private lateinit var itemClickListner: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}

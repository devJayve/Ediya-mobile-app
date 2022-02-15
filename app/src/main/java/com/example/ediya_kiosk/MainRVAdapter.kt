package com.example.ediya_kiosk

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ediya_kiosk_Logic.Main

class MainRvAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    private var menuList = ArrayList<MenuData>()

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
    }

    fun submitList(menuList: ArrayList<MenuData>) {
        this.menuList = menuList
    }
}
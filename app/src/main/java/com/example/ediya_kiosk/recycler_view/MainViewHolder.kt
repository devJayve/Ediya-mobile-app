package com.example.ediya_kiosk.recycler_view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.menu_item.view.*

// 커스텀 뷰홀더
class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val menuNameTextView = itemView.menuNameTV
    val menuPriceTextView = itemView.menuPriceTV
    val menuCategoryTextView = itemView.menuCategoryTV
    val menuImgView = itemView.menuImg

    //데이터와 뷰를 묶는 함수
    fun bind(menuData: MenuData){

        //텍스트 뷰와 실제 태스트 결합
        menuNameTextView.text = menuData.menuName
        menuPriceTextView.text = menuData.menuPrice
        menuCategoryTextView.text = menuData.category
        menuImgView.setImageResource(menuData.menuPhotoImg!!.toInt())

        //이미지 뷰와 실제 이미지 결합
//        Glide
//            .with(App.instance)
//            .load(menuData.menuPhotoImg)
//            .placeholder(menuData.menuPhotoImg)
//            .into(menuImgView)
    }

}
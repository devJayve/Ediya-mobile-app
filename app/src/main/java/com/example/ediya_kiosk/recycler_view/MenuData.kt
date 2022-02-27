package com.example.ediya_kiosk.recycler_view

import android.graphics.drawable.Drawable
import android.util.Log

class MenuData(val category: String, val menuName: String, val menuPrice: String, val menuPhotoImg: String) {

    val TAG: String = "로그"

    // 기본 생성자
    init {
        Log.d(TAG, "MenuData - init() called")
    }
}
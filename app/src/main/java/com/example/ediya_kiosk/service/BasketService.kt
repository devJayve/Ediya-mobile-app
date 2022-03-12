package com.example.ediya_kiosk.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.fragment.basket_fragment

class BasketService : Service() {

    val binder = BasketServiceBinder()

    // Menu Data
    var menuNameList : ArrayList<String> = arrayListOf() //이름
    var menuCountList : ArrayList<String> = arrayListOf() //개수
    var menuTempList : ArrayList<String> = arrayListOf() //온도
    var menuSizeList : ArrayList<String> = arrayListOf() //사이즈
    var menuPriceList : ArrayList<String> = arrayListOf() //단품 가격
    var menuImgList : ArrayList<String> = arrayListOf() //이미지
    var optionCostList : ArrayList<String> = arrayListOf() //옵션 가격
    var totalCostList : ArrayList<String> = arrayListOf() //메뉴 총 가격


    inner class BasketServiceBinder : Binder() {
        fun getService() : BasketService {
            return this@BasketService
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    fun getMenuData(dataList : ArrayList<String>) {
        Log.d("TAG","getMenu $dataList")
        var menuDataList = arrayListOf(
            menuNameList,
            menuCountList,
            menuTempList,
            menuSizeList,
            menuPriceList,
            menuImgList,
            optionCostList,
            totalCostList)

        //데이터 담기
        for (i in dataList.indices) {
            menuDataList[i].add(dataList[i])
        }
    }

    fun putMenuData() : ArrayList<ArrayList<String>> { //데이터 전송
        var menuDataList = arrayListOf(
            menuNameList,
            menuCountList,
            menuTempList,
            menuSizeList,
            menuPriceList,
            menuImgList,
            optionCostList,
            totalCostList)

        return menuDataList
    }

    fun getNotificationInformation(): ArrayList<Int> {
        var totalPriceTxt = 0
        var menuNum = 0
        for (price in totalCostList!!) {
            menuNum++
            totalPriceTxt += price.toInt()
        }
        return arrayListOf(menuNum, totalPriceTxt)
    }

    fun clearList() {
        var menuDataList = arrayListOf(
            menuNameList,
            menuCountList,
            menuTempList,
            menuSizeList,
            menuPriceList,
            menuImgList,
            optionCostList,
            totalCostList)
        for (data in menuDataList) data.clear()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int { //서비스가 실행될 때
        return super.onStartCommand(intent, flags, startId)
    }
}
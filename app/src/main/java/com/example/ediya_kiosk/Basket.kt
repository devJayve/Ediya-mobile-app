package com.example.ediya_kiosk

import com.example.ediya_kiosk.Menu
import com.example.ediya_kiosk.Category

class Basket {
    val menu = Menu()
    val category = Category()
    val menuBasket = menu.menuBasket
    val optionBasket = menu.optionBasket
    val chosenMenuList : MutableList<String> = menuBasket.keys.toMutableList()
    val chosenCountList : MutableList<Int> = menuBasket.values.toMutableList()
    val menuPriceList = menu.menuCost
    val optionPriceList = menu.optionCost
    val chosenOptionList : MutableList<String> = optionBasket.keys.toMutableList()

    var menuCost : MutableList<Int> = menu.menuCost
    var optionCost : MutableList<Int> = menu.optionCost


    fun addMenu() {
        val chosenCategory : Map<String,Int> = category.showMenu()
        menu.choiceMenu(chosenCategory)
    }

    fun informBasket() {
        val totalCost = menuCost.sum() + optionCost.sum()
        var basketLen = menuBasket.size

        if (basketLen == 0 ) {
            println("장바구니에 메뉴가 없습니다.")
        }
        else {
            println("장바구니 정보")
            for (i in 0..basketLen) {
                var menu = chosenMenuList[i]
                var price = menuPriceList[i]
                var count = chosenCountList[i]
                println("메뉴 : $menu | 가격 : $price 원 | 개수 : $count")
            }
            println("현재까지 총 결제 금액은 $totalCost 원 입니다.")
            println("메뉴 삭제 : 1\n개수 수정 : 2\n돌아가기 : 3")
            var answer: Int = readLine()!!.toInt()
            when (answer) {
                1 -> delMenu()
                2 -> adjustNumOfMenu()
            }
        }
    }

    fun delMenu() {
        var i : Int = 0
        println("삭제하실 메뉴를 선택해주세요.")
        for (name in chosenMenuList) {
            i += 1
            println("$i, $name")
        }
        var choice : Int = readLine()!!.toInt()

        if (choice > chosenMenuList.count()) {
            println("장바구니에 $choice 번째 메뉴는 없습니다.")
            return delMenu()
        }
        else if (choice == 0) {
            return informBasket()
        }
        else {
            println("$choice 번째 메뉴를 삭제합니다.")
            chosenMenuList.removeAt(choice)
        }
    }

    fun adjustNumOfMenu() {

    }
}
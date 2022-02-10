package com.example.ediya_kiosk_Logic

class Menu {
    val optionList : Map<String,Int> = mapOf("+에스프레소 1샷" to 500,
        "+휘핑 크림" to 300,
        "+(시럽)바닐라" to 300,
        "+(시럽)헤이즐넛" to 300,
        "+(토핑)카라멜 소스" to 300,
        "+(토핑)초콜렛 소스" to 500,
        "+(토핑)초콜렛 칩" to 500,
    )
    val optionKeyList = optionList.keys.toList()
    val optionValueList = optionList.values.toList()

    var menuCost = mutableListOf<Int>()
    var optionCost = mutableListOf<Int>()
    var menuBasket : MutableMap<String,Int> = mutableMapOf()
    var optionBasket : MutableMap<String,Int> = mutableMapOf()

    fun choiceMenu(category : Map<String,Int>) {
        val menuList : List<String> = category.keys.toList()
        val priceList : List<Int> = category.values.toList()

        println("메뉴를 선택해주세요.")
        var menuChoiceNum : Int = readLine()!!.toInt()
        var selectedMenu : String = menuList[menuChoiceNum-1].toString()
        var selectedMenuPrice = priceList[menuChoiceNum-1]
        println("\"$selectedMenu\" 메뉴를 선택하였습니다.")
        countMenu(selectedMenuPrice, selectedMenu)
    }

    fun countMenu(MenuPrice : Int, selectedMenu : String) {
        println("$selectedMenu 의 원하시는 개수를 입력해주세요.")
        var menuCountNum = readLine()!!.toInt()
        if (menuCountNum != 0) {
            menuBasket.put(selectedMenu, menuCountNum)
            menuCost.add(MenuPrice*menuCountNum)
        }
        else {
            println("오류 :: 잘못된 개수입니다.")
            countMenu(MenuPrice, selectedMenu)
        }
        choiceOption()
    }

    fun choiceOption() {
        println("옵션을 선택하시겠습니까 ?\n(예 : 1, 아니오 : 2)")
        var optAnswerNum = readLine()!!.toInt()
        if (optAnswerNum == 1) {
            var i = 1
            for ((key, value) in optionList) {
                println("$i. $key | $value 원")
                i += 1
            }
            var optChoiceNum : Int
            while (optAnswerNum != 0) {
                println("원하시는 옵션을 선택해주세요. (취소 : 0)")
                optAnswerNum = readLine()!!.toInt()
                optionBasket.put(optionKeyList[optAnswerNum-1],optionValueList[optAnswerNum-1])
                optionCost.add(optionValueList[optAnswerNum-1])
                println("선택된 옵션은 ${optionBasket.keys} 입니다.")
                println("옵션을 더 추가하시겠습니까?\n(예 : 1, 아니오 : 2")
                var optionPlusNum : Int = readLine()!!.toInt()
                when (optionPlusNum) {
                    1 -> continue
                    2 -> break
                }

            }

        }
    }

}
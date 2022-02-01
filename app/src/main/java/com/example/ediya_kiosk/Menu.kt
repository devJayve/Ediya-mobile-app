package com.example.ediya_kiosk

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
}
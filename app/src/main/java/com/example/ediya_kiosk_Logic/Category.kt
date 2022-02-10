package com.example.ediya_kiosk_Logic

class Category {
    val coffeeMenu : Map<String, Int> = mapOf(
        "아메리카노" to 3200,
        "카페라떼" to 3700,
        "바닐라라떼" to 3900,
        "카푸치노" to 3700,
        "카페모카" to 3900,
        "카라멜마끼아또" to 3900,
        "화이트초콜릿모카" to 3900,
        "민트모카" to 4200,
        "에스프레소" to 2900,
        "에스프레소 콘피냐" to 3200,
        "에스프레소 마끼아또" to 3200,
        "연유 카페라떼" to 3800,
        "콜드브루 라떼" to 4200,
        "콜드브루 화이트비엔나" to 4500,
        "콜드브루 니트로" to 3900,
        "콜드브루 아메리카노" to 3700,
        "흑당 콜드브루" to 3600,
        "콜드브루 크림넛" to 4500,
        "콜드브루 티라미수" to 4800,
        "연유 콜드브루" to 4300,
        "아인슈페너" to 3700,
        "콜드브루 아인슈페너" to 4200,
        "블랙모카슈페너" to 4300
    )

    val beverageMenu : Map<String, Int> = mapOf(
        "초콜릿" to 3700,
        "민트 초콜릿" to 4000,
        "토피넛 라떼" to 4000,
        "녹차라떼" to 3700,
        "화이트 초콜릿" to 3700,
        "고구마 라떼" to 4000,
        "브라우니 쇼콜라" to 4200,
        "이곡 라떼" to 3500,
        "소보로 크림 고구마라떼" to 4500,
        "옥수수 라떼" to 4000,
        "골드키위주스" to 4200,
        "딸기주스" to 4200,
        "홍시주스" to 4200,
        "흑당라떼" to 3300,
        "봄 딸기라떼" to 3500,
        "봄 딸기 밀크티" to 4200,
        "달고나라떼" to 3500,
        "골든바닐라슈페너" to 4500,
        "크림 달고나 라떼" to 3900,
        "딸기라떼" to 3500,
    )

    val flatcinoMenu : Map<String, Int> = mapOf(
        "커피 플랫치노" to 3500,
        "망고 플랫치노" to 3500,
        "플레인 요거트 플랫치노" to 4200,
        "딸기 요거트 플랫치노" to 4200,
        "모카 플랫치노" to 3800,
        "초콜릿 칩 플랫치노" to 4200,
        "블루베리 요거트플랫치노" to 4200,
        "꿀복숭아 플랫치노" to 3500,
        "카라멜 플랫치노" to 3800,
        "민트 초콜릿칩 플랫치노" to 4200,
        "그린애플 플랫치노" to 3800,
        "녹차 플랫치노" to 4200,
        "자몽 플랫치노" to 3800,
        "그린파인 후룻치노" to 3500,
        "블루코코 후룻치노" to 3800,
        "딸기 복숭아 요거트 플랫치노" to 4500
    )

    val bubbleMilkTeaMenu : Map<String, Int> = mapOf(
        "버블 크림 밀크티" to 4300,
        "버블 베리 밀크티" to 4300,
        "버블 베리 쉐이크" to 5300,
        "버블 밀크티 쉐이크" to 5300,
        "버블 흑당 라떼" to 4300,
        "버블 흑당 콜드브루" to 4700,
    )

    val bakeryMenu : Map<String, Int> = mapOf(
        "(와플)플레인" to 2300,
        "(와플)생크림" to 2500,
        "(와플)메이플" to 2800,
        "(와플)크림치즈" to 3000,
        "프레즐" to 2300,
        "(번)오리지널" to 1900,
        "(번)치즈" to 2000,
        "(브레드)허니카라멜" to 4600,
        "(브레드)메이플넛" to 4800,
        "생크림 초코쿠키빵" to 1500,
        "생크림 팥빵" to 1700,
        "생크림 티라미수" to 1700,
        "크림치즈" to 800,
    )


    val menuCategory : Map<Int,Map<String,Int>> = mapOf(
        1 to coffeeMenu,
        2 to beverageMenu,
        3 to flatcinoMenu,
        4 to bubbleMilkTeaMenu,
        5 to bakeryMenu
    )

    fun showCategory() : Int {
        println("원하시는 카테고리를 선택해주세요.")
        println("1 : COFFEE\n2: 베버러지\n3 : 플랫치노\n4 :  버븚밀크티\n5 : 베이커리")
        var choiceNum = readLine()!!.toInt()
        return choiceNum
    }

    fun showMenu() : Map<String,Int> {
        var choiceNum = showCategory()
        var i = 1
        var chosenCategory = menuCategory[choiceNum]
        for ((key, value) in chosenCategory!!) {
            println("$i. $key | $value 원")
            i += 1
        }
        return chosenCategory
    }
}
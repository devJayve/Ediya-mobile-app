package com.example.ediya_kiosk

class Payment {

    var discountAmount : Int = 0
    var answerNum : Int? = 0
    var orderType : Int = 0


    fun choiceOrderType() : Unit {
        println("매장 주문은 '1', 포장 주문은 '2'를 선택해주세요. : ")
        answerNum = readLine()!!.toInt()
        when (answerNum) {
            1 -> {
                print("매장 주문을 선택하셨습니다.")
                orderType = 1
            }
            2 -> {
                print("포장 주문을 선택하셨습니다.")
                orderType = 2
            }
        }
    }

//    fun payByCard

}
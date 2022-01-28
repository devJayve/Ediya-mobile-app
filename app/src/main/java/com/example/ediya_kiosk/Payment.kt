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

    fun askMembership() {
        print("멤버십을 적립하시겠습니까?\n(예 :1, 아니오 :2)")
        var answer = readLine()!!.toInt()
        if (answer ==1)
            addMembership()
    }

    fun addMembership() {
        println("멤버십 포인트를 적립할 전화번호를 입력해주세요. (취소 : 0)")
        val membershipNum : String = readLine()!!.toString()
        while ((membershipNum.length == 11)) {

        }
    }
//    fun payByCard

}
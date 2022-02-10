package com.example.ediya_kiosk_Logic

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
            if(membershipNum.length != 11) {
                println("전화번호 11자리를 다시 확인해주세요.")
                continue
            }
            else if(membershipNum == "0")
                return askMembership()
        print("멤버십 할인 500원이 적용됩니다.")
            discountAmount -= 500
        }
    }
    fun payByCard(menuCost : MutableList<Int>, optionCost: MutableList<Int>) {
        var totalPayment = menuCost.sum() + optionCost.sum()
        println("총 결제 금액 : $totalPayment 원")
    }

    fun payByCash(menuCost : MutableList<Int>, optionCost: MutableList<Int>) {
        println("현금 결제를 진행합니다.")
        var totalPayment = menuCost.sum() + optionCost.sum()
        println("총 결제 금액 : $totalPayment 원")
        println("투입구에 현금을 투입해주세요.")
        var cashInput : Int = readLine()!!.toInt()
        if (cashInput < totalPayment )
            println("잔액이 부족합니다.")

    }

}
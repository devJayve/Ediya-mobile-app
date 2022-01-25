package com.example.ediya_kiosk

import com.example.ediya_kiosk.Payment
import com.example.ediya_kiosk.Basket


class Main {
    val payment = Payment()
    val basket = Basket()

    var answerNum : Int = 0
    var selectNum : Int = 0

    fun loadKiost() {
        payment.choiceOrderType()

        do {
            println("원하시는 서비스를 선택해주세요.")
            println("""
                1. 메뉴 선택/추가
                2. 장바구니 수정
                3. 결제
            """.trimIndent())
            selectNum = readLine()!!.toInt()
        }while ( selectNum < 4 )

        when (selectNum) {
            1-> payment
        }
    }
}


fun main() {
    val user = Main()
}
package com.example.ediya_kiosk_Logic


class Main {
    val payment = Payment()
    val basket = Basket()

    var answerNum : Int = 0
    var selectNum : Int = 0


    fun loadKiosk(userName : String) {
        println("환영합니다 $userName 고객님")
        payment.choiceOrderType()

        while (selectNum < 4) {
            println("원하시는 서비스를 선택해주세요.")
            println("""
                1. 메뉴 선택/추가
                2. 장바구니
                3. 결제
            """.trimIndent())
            selectNum = readLine()!!.toInt()

            if (selectNum == 1)
                basket.addMenu()
            else if (selectNum == 2)
                basket.informBasket()
            else {
                payment.askMembership()
                println("원하시는 결제수단을 선택해주세요.")
                print("""
                1.카드 결제
                2.현금 결제
                3. 취소
            """.trimIndent())
                var payment_answer : Int = readLine()!!.toInt()
                if (payment_answer == 1) {
                    payment.payByCard(basket.menuCost, basket.optionCost)
                }
                else if (payment_answer ==2) {
                    payment.payByCash(basket.menuCost, basket.optionCost)
                }
            }
        }
    }
}

    fun quitKiosk() {
        print("키오스크를 종료합니다. \n이용해주셔서 감사합니다.")
    }


fun main() {
    val user = Main()
    print("이름을 입력해주세요 : ")
    val userName : String = readLine().toString()
    user.loadKiosk(userName)
}
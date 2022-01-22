package com.example.stage

//fun main() {

//    printSum(1, 2)
//    var tmpCal = Cal(1, 2)

//    println("hello") // 개행 기능
//    print("world")
//
//    var num1: String? = ""
//    num1 = readLine() // kotiln은 type checker 기능 작동
//// 첫번째 해결 방법 > !!readLine() : Null값은 절대 넣지 않는다는 뜻
//// 두번째 해결 방법 > String? : 잘못되었을 때 꺼지진 않고 다음걸로 넘어감.
//
//    var num2: Int? = 0
//    num2 = readLine()!!.toInt()
////Null 값이 아닐 때만 toInt를 적용해달라는 뜻, Null이면 무시하고 지나감.
//// 이것을 보통 Narrowing 이라고 함. (프로그램이 중단되지 않게 하는 장치)

//    var num: Int = 10
//
//    if (num>5) {
//        println("5보다 큽니다.")
//    } else if (num <= 5) {
//        println("5보다 작거나 같습니다.")
//    }
//
//    while (num != 0) {
//        // 내용
//    }
//
//    for (index in 0 until 10 step 2) { // 2간격으로 출력
//        println(index)
//    }
//
//    for (index in 10 downTo 0) { // 거꾸로 출력
//        println(index)
//    }
//
//    for (index in 0..10) { // 0부터 10 포함해서 출력
//        println(index)
//    }

//    fun sum(a: Int, b: Int): Int { //a,b의 타입, return값의 타입
//        return a + b
//    }

class Cal(number : Int, number2: Int) {

    var num: Int = 0 // 멤버변수  **멤버변수 선언은 위에서 할 것
    var num2: Int = 0

    var list = arrayOf(1, 2, 3)
    var list2 : Array<Any> = arrayOf("juyeong", 2, 3)
    var list3 = Array<Int?>(3) { null } //컬랙션이라고 부르며, 라이브러리임.
    // 컬랙션은 속도가 바르나, 크기를 못바꿈.

    init {   // 생성자
       num = number
       num2 = number2

//        list.count() 개수
//        list.first() 처음 값
//        list.last() 마지막 값
//        list.sum() 합
//        list.average() 평균

       list = list.plus(4)
        list2 = list2.plus(4)
        printList()
    }

    fun sum(a: Int, b: Int) : Int {  // 멤버함수
        return(a + b)
    }

    fun printList() {
        for (index in 0..2) {
            println(index)
        }
    }
}

fun main() {

    var name: String = "stageus"
    println("내 이름은 ${name} 입니다.")
    var tmpCal = Cal(1, 2)
}


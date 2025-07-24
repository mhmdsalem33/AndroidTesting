package com.salem.androidtesting.ramadan_test.test_doubles.stub


interface PaymentService {
    fun processPayment(amount : Int): Boolean
}

class PaymentProcessor(private val paymentService: PaymentService){
    fun pay(amount : Int ) : Boolean{
        val result = paymentService.processPayment(amount)
        println("payment status is $result")
        return  result
    }
}
package com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed


data class Dependency1(val value : Int )
data class Dependency2(val value : Int )


class Calculator( val dependency1: Dependency1 , val dependency2: Dependency2 ){
    fun add() = dependency1.value + dependency2.value
    fun subtract() = dependency1.value - dependency2.value
}
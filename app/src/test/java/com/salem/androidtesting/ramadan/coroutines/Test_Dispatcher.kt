package com.salem.androidtesting.ramadan.coroutines


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class Coroutines{

    @Test
    fun `test Coroutine Schedule`(){

        val schedule = TestCoroutineScheduler()

        println(schedule.currentTime) // 0
        schedule.advanceTimeBy(1000)
        println(schedule.currentTime) // 1
        schedule.advanceTimeBy(1000)
        println(schedule.currentTime) // 2

    }
    @Test
    fun `test Coroutine StandardTestDispatcher`(){

        val schedule = TestCoroutineScheduler()

        val testDispatcher = StandardTestDispatcher(schedule)

        // parallel
        CoroutineScope(testDispatcher).launch {  // Coroutine Scope 1 == queue 1
            println("Some Work 1")
            delay(1000)
            println("Some Work 2")
            delay(1000)
            println("Some Work 3")
        }

        // parallel
        CoroutineScope(testDispatcher).launch {  // Coroutine Scope 2 == queue 2
            delay(500)
            println("Different Work")
        }

        println("${schedule.currentTime} Before")
        schedule.advanceUntilIdle()
        println("${schedule.currentTime} After")

        // schedule.advanceUntilIdle() -> btsha3al el queue coroutines  and wait the results
        // De betKon Mas2ola enHa Tesh3al El Coroutine Scopes  ely Fo2 1 and 2  and wait the result
    }



    @Test
    fun `test Coroutine`(){

        val unConfinedTestDispatcher = UnconfinedTestDispatcher()

        val testDispatcher  = StandardTestDispatcher()

        CoroutineScope(testDispatcher).launch { // one
            delay(2)
            println("Done")
        }

        CoroutineScope(testDispatcher).launch { // two
            delay(4)
            println("Done2")
        }

        CoroutineScope(testDispatcher).launch { // three
            delay(6)
            println("Done3")
        }

        testDispatcher.scheduler.advanceTimeBy(4 ) //  hatsh3l l7ad el two bas
        testDispatcher.scheduler.runCurrent()

    }

    @Test
    fun `test unConfined Test Dispatcher` (){

        val unConfinedTestDispatcher = UnconfinedTestDispatcher()
        val standardTestDispatcher   = StandardTestDispatcher()

        runTest (context = unConfinedTestDispatcher) {

            launch {
//                delay(1000L)
                println("The first coroutine is completed")
            }
            launch {
                println("The second coroutine is completed")

            }

            println("done!")

        }
//        standardTestDispatcher.scheduler.advanceUntilIdle()

    }
}

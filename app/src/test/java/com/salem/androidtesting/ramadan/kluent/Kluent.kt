package com.salem.androidtesting.ramadan.kluent

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.junit.Test

class Kluent {

    @Test
    fun `test flow itself`() = runTest{
        val flow = flowOf( 1 , 2 , 3 , 4)

        flow.test {
            1 shouldBeEqualTo awaitItem()
            2 shouldBeEqualTo awaitItem()
            3 shouldBeEqualTo awaitItem()
            4 shouldBeEqualTo awaitItem()
            awaitComplete()
        }
    }

    @Test
    fun `test flow of strings`() =  runTest{
        val flow = flowOf("mohamed" , "salem")
        flow.test {
            "mohamed" shouldBeEqualTo awaitItem()
            "salem"   shouldBeEqualTo awaitItem()
            awaitComplete()
        }
    }

    @Test
    fun `test should be not equals`() = runTest{
        val flow = flowOf( "salem")

        flow.test {
            "sayed" shouldNotBeEqualTo awaitItem()
             awaitComplete()
        }
    }


}
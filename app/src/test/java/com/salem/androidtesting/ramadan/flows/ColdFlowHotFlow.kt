package com.salem.androidtesting.ramadan.flows

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.amshove.kluent.shouldBeEqualTo


class ColdFlow {

    @Test
    fun `test cold flow`() = runTest {
        val flow = flowOf( 1 , 2 , 3 ).map { it * 10 }
        flow.test {
            awaitItem() shouldBeEqualTo  10
            awaitItem() shouldBeEqualTo  20
            awaitItem() shouldBeEqualTo  30
            awaitComplete()
        }
    }
}
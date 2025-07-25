package com.salem.androidtesting.ramadan.flows

import app.cash.turbine.test
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.amshove.kluent.shouldBeEqualTo
import kotlin.test.assertEquals


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

    // with hot flow we don't use a awaitComplete()

    @Test
    fun `convert cold flow to hot flow`() = runTest {
        val flow = flowOf(1,2,3).map { it * 10 } .stateIn(this)

        flow.test { awaitItem() shouldBeEqualTo 30 }
    }
}
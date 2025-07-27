package com.salem.androidtesting.ramadan.flows

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
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

    // with hot flow we don't use a awaitComplete()

    @Test
    fun `convert cold flow to hot flow`() = runTest {
        val flow = flowOf(1,2,3).map { it * 10 } .stateIn(this)

        flow.test { awaitItem() shouldBeEqualTo 30 }
    }




}

sealed class UIState{
    data object Loading : UIState()
    data object Success : UIState()
}
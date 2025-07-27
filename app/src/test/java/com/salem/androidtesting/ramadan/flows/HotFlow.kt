package com.salem.androidtesting.ramadan.flows

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class HotFlow {

    @Test
    fun `test state flow`() = runTest {
        val flow = MutableStateFlow<UIState>(UIState.Loading)

        flow.test {
            awaitItem() shouldBe UIState.Loading
            flow.tryEmit(UIState.Success)
            awaitItem() shouldBe UIState.Success
        }
    }

    @Test
    fun `test shared flow`() = runTest {
        val flow = MutableSharedFlow<Int>(replay = 1)  // replay convert a shared flow to state flow
        flow.emit(1)
        flow.test {
            awaitItem() shouldBeEqualTo 1
        }
    }

}
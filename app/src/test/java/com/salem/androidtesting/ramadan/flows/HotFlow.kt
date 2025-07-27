package com.salem.androidtesting.ramadan.flows

import app.cash.turbine.test
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.shareIn
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


    @Test
    fun `test shared flow with SharingStarted WhileSubscribed`() = runTest {

        val flow = flowOf(
            "Event 1",
            "Event 2",
            "Event 3"
        )

        val sharedFlow = flow
            .onCompletion {
                println("Shared flow completed")
            }.shareIn(
                this,
                SharingStarted.WhileSubscribed() ,
                1
            )

        // SharingStarted.WhileSubscribed()
        // ✅ Start collecting data only when someone is listening to the flow
        // ⏸️ Stop collecting when no one is listening anymore.

        sharedFlow.test {
            awaitItem() shouldBeEqualTo "Event 1"
            awaitItem() shouldBeEqualTo "Event 2"
            awaitItem() shouldBeEqualTo "Event 3"
        }
        coroutineContext.cancelChildren()
    }

    @Test
    fun `test shared flow with SharingStarted Lazily`() = runTest {

        val flow = flowOf(
            "Event 1",
            "Event 2",
            "Event 3"
        )

        val sharedFlow = flow
            .onCompletion {
                println("Shared flow completed")
            }.shareIn(
                scope   =  this,
                started = SharingStarted.Lazily ,
                replay  = 1
            )

        // SharingStarted.Lazily
        // ✅ The flow starts when the first listener appears.
        // ❌ It does not stop when no one is listening.

        sharedFlow.test {
            awaitItem() shouldBeEqualTo "Event 1"
            awaitItem() shouldBeEqualTo "Event 2"
            awaitItem() shouldBeEqualTo "Event 3"
        }
        coroutineContext.cancelChildren()
    }


}
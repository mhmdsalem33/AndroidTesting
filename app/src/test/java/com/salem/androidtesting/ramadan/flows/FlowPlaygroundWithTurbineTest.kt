package com.salem.androidtesting.ramadan.flows

import app.cash.turbine.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class FlowPlaygroundWithTurbineTest {

    /**
     * Turbine ->
     * awaitItem()
     * awaitComplete()
     * awaitError()
     * */

    @Test
    fun `test flow itself`() = runTest{
        val flow = flowOf(1 , 2 , 3 , 4)

        flow.test {
            assertEquals( 1 , awaitItem())
            assertEquals( 2 , awaitItem())
            assertEquals( 3 , awaitItem())
            assertEquals( 4 , awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test flow consumer cont`() = runTest{
        val flow = flow{
            emit(1)
            emit(2)
            emit(3)
        }

        flow.test {
            assertEquals( 1 , awaitItem())
            assertEquals( 2 , awaitItem())
            assertEquals( 3 , awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `test flow consumer with exception`() = runTest {
            val flow = flow {
                emit(1)
                throw IllegalStateException("unExpected error")
            }
        flow.test {
            assertEquals(1 , awaitItem())
            assertEquals("unExpected error" , awaitError().message)
        }
    }





}
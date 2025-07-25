package com.salem.androidtesting.ramadan.flows

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FlowsBackgroundTest {

    // testing flows types :
    //  1- testing flow itself
    //  2- testing flow consumer

    @Test
    fun `test flow itself`() = runTest{
        val flow = flowOf(1 , 2 , 3 , 4)
        val result = flow.toList()
        assertEquals(listOf(1 , 2 ,3 ,4) , result)
    }


    @Test
    fun `test flow consumer`() = runTest{

        val flow = flowOf(1,2,3)

        val result = mutableListOf<Int>()

        flow.collect { result.add(it) }

        assertEquals(listOf(1,2,3) , result)
    }

    @Test
    fun `test flow consumer cont`() = runTest{
        val flow = flow{
            emit(1)
            emit(2)
            emit(3)
        }

        val result = mutableListOf<Int>()

        flow.collect { result.add(it) }

        assertEquals(listOf( 1 , 2 , 3 ) , result )
    }

    @Test
    fun `test flow consumer cont with delay`() = runTest{
        val flow = flow {
            for (i in 1..3){
                emit(i)
            }
        }

        val result = mutableListOf<Int>()

        flow.onEach {
          result.add(it)
        }.launchIn(this)

        advanceUntilIdle()// wait all coroutines to finished

        assertEquals(listOf(1,2,3) , result)

    }

    @Test
    fun `test flow consumer with exception`() = runTest {
        val result = mutableListOf<Int>()
        try {
            val flow = flow {
                emit(1)
                throw IllegalStateException("error")
            }

            flow.collect {
                result.add(it)
            }

        }catch ( e : Exception ){
            println("exception happened ${e.message}")
            result.add(-1)
        }

        assertEquals(listOf(1 , -1) , result)
    }



}
package com.salem.androidtesting.ramadan.test_boubles


import com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed.Calculator
import com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed.Dependency1
import com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed.Dependency2
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SpyStubTest {

    @Test
    fun `est spy stub nature of mockk`() {
        val dependency1: Dependency1 = mockk()

        val dependency2: Dependency2 = Dependency2(4)  // real implementation
        val spyDependency2 = spyk(dependency2) //  spy real implementation

        every { dependency1.value } returns 3  // stubbing

        val calculator = Calculator(dependency1, spyDependency2)
        val result = calculator.add()

        assertEquals(7, result)

    }







}
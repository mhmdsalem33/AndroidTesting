package com.salem.androidtesting.ramadan.test_boubles.strict_relaxed

import com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed.Calculator
import com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed.Dependency1
import com.salem.androidtesting.ramadan_test.test_doubles.strick_relaxed.Dependency2
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

class CalculatorTest {

    @Test
    fun `test strict nature of mockk`() {
        val dependency1: Dependency1 = mockk()
        val dependency2: Dependency2 = mockk()

        // stubbing
        every { dependency1.value } returns 3
        every { dependency2.value } returns 4

        val calculator = Calculator(dependency1, dependency2)
        val result = calculator.add()
        assertEquals(7, result)
    }

    @Test
    fun `test relaxed nature of mockk`() {
        val dependency1: Dependency1 = mockk(relaxed = true) // return default value
        val dependency2: Dependency2 = mockk()    // must make a stubbing and return result
        every { dependency2.value } returns 3     // stubbing
        val calculator = Calculator(dependency1, dependency2)
        val result = calculator.add()
        assertEquals(3, result)
    }

}


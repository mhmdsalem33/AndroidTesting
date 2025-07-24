package com.salem.androidtesting.ramadan.test_boubles.stub

import com.salem.androidtesting.ramadan_test.test_doubles.stub.PaymentProcessor
import com.salem.androidtesting.ramadan_test.test_doubles.stub.PaymentService
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertTrue


/**
 *   every {  } ma3nha Kool lama a3ml call lel ben el { } return result
 *   every { paymentService.processPayment(10)  } returns true
 *
 *   any() -> ma3nha BtMatch any result
 */
class PaymentProcessorTest {

    @Test
    fun `test stub test doubles scenario`(){
        val paymentService : PaymentService = mockk()
        val paymentProcessor  = PaymentProcessor(paymentService)

        // stub
        every { paymentService.processPayment(any())  } returns true  // انا اللي حددت انو يرجع True

        val result = paymentProcessor.pay(10)
        assertTrue(result)

    }
}
package com.salem.androidtesting.ramadan.customers

import com.salem.androidtesting.ramadan_test.customer.Customer
import com.salem.androidtesting.ramadan_test.customer.CustomerController
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class CustomersControllerTest {


    private  var controller : CustomerController ? = null

    @Before
    fun setup(){
        controller = CustomerController()
        println("setup is called")
    }


    @Test
    fun `Given condition when action then expectation`(){

    }

    // TDD
    // RED  --> GREEN  --> REFACTOR

    @Test
    fun `Given valid customer when call addCustomer() then we should expect customer list has one item`(){
        val customer   = Customer( firstName = "Mohamed" , lastName = "Salem" ,  phoneNumber = "12345678911" )
        controller?.addCustomer(customer)
        assertEquals( 1 , controller?.getCustomers()?.size  )
    }

    // RED  --> GREEN  --> REFACTOR

    @Test
    fun `Given Customer with two chars in first name when call addCustomer() we should expect customers is empty `(){
        val customer   = Customer( firstName = "mo" , lastName = "Salem" ,  phoneNumber = "123456789" )
        controller?.addCustomer(customer)
        assertTrue(controller?.getCustomers()?.isEmpty() == true)
    }

    // RED  --> GREEN  --> REFACTOR

    @Test
    fun `Given customer with two chars in last name when call addCustomer() we should expect customer is empty`(){
        val customer   = Customer( firstName = "mohamed" , lastName = "sa" ,  phoneNumber = "123456789" )
        controller?.addCustomer(customer)
        assertTrue(controller?.getCustomers()?.isEmpty() == true)
    }

    // RED  --> GREEN  --> REFACTOR
    @Test
    fun `Given customer with empty first name and empty last name when call addCustomer() we should expect is empty`(){
        val customer   = Customer( firstName = "" , lastName = "" ,  phoneNumber = "123456789" )
        controller?.addCustomer(customer)
        assertTrue(controller?.getCustomers()?.isEmpty() == true)
    }


    @Test
    fun `Given a customer not valid with a phone number when call addCustomer() we should expect is empty`(){
        val customer    = Customer( firstName = "mohamed" , lastName = "salem" , phoneNumber = "123456" )
            controller?.addCustomer(customer)
        assertTrue(controller?.getCustomers()?.isEmpty() == true)
    }

    @Test
    fun `Given a customer not valid with 12 digits a phone number when call addCustomer() then we should expect is empty`(){
        val customer    = Customer( firstName = "mohamed" , lastName = "salem" , phoneNumber = "123456789123" )
        controller?.addCustomer(customer)
        assertTrue(controller?.getCustomers()?.isEmpty() == true)
    }

    @Test
    fun `Given a valid customer phone number with 11 digits when call addCustomer() then we should expect that customers has one item`(){
        val customer    = Customer( firstName = "mohamed" , lastName = "salem" , phoneNumber = "01116475199" )
        controller?.addCustomer(customer)
        assertEquals(1 , controller?.getCustomers()?.size)
    }


    @Test(expected = IllegalArgumentException::class)
    fun `Given a not valid customer with phone number is chars when call addCustomer() then we should expect is empty`(){
        val customer    = Customer( firstName = "mohamed" , lastName = "salem" , phoneNumber = "asdasdfdswq" )
        controller?.addCustomer(customer)
        assertTrue(controller?.getCustomers()?.isEmpty() == true)
    }


    @Ignore("Ignore this test now")
    @Test
    fun `Given a customer with duplicated when call addCustomer() then we should expect customers added one item only`(){
        val customer    = Customer( firstName = "mohamed" , lastName = "salem" , phoneNumber = "11111111111" )
        controller?.addCustomer(customer)
        controller?.addCustomer(customer)
        println("Customers count is "+controller?.getCustomers()?.size + " Customers " +controller?.getCustomers())
        assertEquals(1  , controller?.getCustomers()?.size )
    }


    @After
    fun close(){
        controller = null
        println("close .... called")
    }

}




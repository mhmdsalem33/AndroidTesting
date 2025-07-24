package com.salem.androidtesting.ramadan_test.customer

class CustomerController {

    private val customerList = mutableListOf<Customer>()

    fun addCustomer(customer : Customer){
        if (isValidCustomer(customer.firstName) && isValidCustomer(customer.lastName) && isValidPhoneNumber(customer.phoneNumber) && !isCustomerDuplicated(customer)){
            customerList.add(customer)
        }
    }

    fun getCustomers() : List<Customer>{
        return customerList.toList()
    }


    private fun isValidCustomer( input : String ): Boolean = input.length >= 3

    private fun isValidPhoneNumber(phoneNumber : String) : Boolean {
        if (phoneNumber.all { chars -> chars.isLetter() })
            throw IllegalArgumentException("phone number is not valid")
       return phoneNumber.length == 11  && phoneNumber.all { chars -> chars.isDigit() }
    }

    private fun isCustomerDuplicated(customer: Customer): Boolean{
        if (customer in customerList) return true
        return false
    }

}
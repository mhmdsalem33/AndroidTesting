package com.salem.androidtesting.philip.RegistrationUtils

object RegistrationUtils {

    /**
     * the input is not valid if
     * .... username / password is empty
     * .... username / is already taken
     * .... the confirmed password is not the same real password
     * .... the password is less than 2 digits
     * */

    private val existingUsers = listOf( "Peter" , "Carl" )

    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String
    ): Boolean {

        if (userName.isEmpty() || password.isEmpty()) return false

        if (userName in existingUsers) return false

        if (password != confirmedPassword)  return false

        if (password.count { it.isDigit() } < 2 ) return false

        return true
    }


}
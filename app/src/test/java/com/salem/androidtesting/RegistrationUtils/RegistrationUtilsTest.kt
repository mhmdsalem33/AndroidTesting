package com.salem.androidtesting.RegistrationUtils

import com.google.common.truth.Truth.assertThat
import com.salem.androidtesting.philip.RegistrationUtils.RegistrationUtils
import org.junit.Test

class RegistrationUtilsTest {


    @Test
    fun `empty username return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }



    @Test
    fun `valid username and correctly repeated password return true`(){
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "Mohamed",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username is already exists return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
            userName =  "Carl",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }



    @Test
    fun `empty password return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
            userName =  "Mohamed",
            password = "",
            confirmedPassword = ""
        )
        assertThat(result).isFalse()
    }


    @Test
    fun `less than 2 digits return false`(){
        val result = RegistrationUtils.validateRegistrationInput(
            userName =  "Mohamed",
            password = "asdb",
            confirmedPassword = "asdb"
        )
        assertThat(result).isFalse()
    }


}
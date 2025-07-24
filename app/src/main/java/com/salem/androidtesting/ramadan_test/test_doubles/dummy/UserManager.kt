package com.salem.androidtesting.ramadan_test.test_doubles.dummy

class UserManager( logger: Logger ) {

    private val userList = mutableListOf<User>()

    fun addUser(user: User){
        userList.add(user)
    }

    fun getUsers() = userList.toList()
}


data class User( val firstName : String )



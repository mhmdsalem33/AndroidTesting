package com.salem.androidtesting.ramadan_test.test_doubles.mock



interface UserService{
    fun getUserCount(): Int
}

class UserServiceImpl : UserService{
    override fun getUserCount() = 20
}


class PremiumUsersManager (val userService: UserService) {
    fun getUsersCount() = userService.getUserCount()
}


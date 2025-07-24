package com.salem.androidtesting.ramadan.test_boubles.mock_spy

import com.salem.androidtesting.ramadan_test.test_doubles.mock.PremiumUsersManager
import com.salem.androidtesting.ramadan_test.test_doubles.mock.UserService
import com.salem.androidtesting.ramadan_test.test_doubles.mock.UserServiceImpl
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test
import kotlin.test.assertEquals

/***
 * verify {} -> بتآكد ان الحاجه اتعمل ليها  Call
 * verify(atLeast = 1){ userService.getUserCount() }
 * verify(atLeast = 1) that verify the fun called one time only
 * with mock we use a mockk instead of the real impl
 * we will verify the mockk
 */

class PremiumUsersTest {


    // MOCKING EXAMPLE
    @Test
    fun `test mocking test doubles`(){
        val userService : UserService = mockk()
        val premiumUserManager = PremiumUsersManager(userService)

        every { premiumUserManager.getUsersCount() } returns 10 // it's will verify the result will be 10

        val result = premiumUserManager.getUsersCount()
        assertEquals( 10 , result)

        verify(atLeast = 1){
            userService.getUserCount()  // here we will verify the mockk
        }
    }

    /**
     * Spy
     * 1- It's will use the real implementation -> concrete impl
     * 2- spy the real impl
     * 3- pass it to the as a param
     * 4- we will verify the spy
     * */

    // SPY EXAMPLE

    @Test
    fun `test spy test doubles`(){
        val userService: UserService = UserServiceImpl()
        val spyUserService = spyk(userService)
        val premiumUsersManager = PremiumUsersManager(spyUserService)

        val result = premiumUsersManager.getUsersCount()
        assertEquals( 20 , result )

        verify(atLeast = 1){
            spyUserService.getUserCount() // here we will verify the spy
        }

    }


}



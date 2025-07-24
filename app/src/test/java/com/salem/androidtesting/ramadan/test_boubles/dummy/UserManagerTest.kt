package com.salem.androidtesting.ramadan.test_boubles.dummy

import com.salem.androidtesting.ramadan_test.test_doubles.dummy.Logger
import com.salem.androidtesting.ramadan_test.test_doubles.dummy.User
import com.salem.androidtesting.ramadan_test.test_doubles.dummy.UserManager
import io.mockk.mockk
import org.junit.Test
import kotlin.test.assertEquals

/** El fekra fe el dummy Eny ba3ml mockk Le ay 7aga ana mesh bast5dmha zay
 *  El Logger and 3mlt meno mockk instance
 *  3shan ana mesh hast5dm el logger
 *  ana hast5dm el userManager bas
 *  mesh hast5dm el logger fe 7aga
 */
class UserManagerTest {

    @Test
    fun `test dummy test doubles`(){
        val logger : Logger = mockk()
        val userManager = UserManager(logger)
        val user = User(firstName = "Mohamed")
        userManager.addUser(user)
        assertEquals(1, userManager.getUsers().size)

    }
}
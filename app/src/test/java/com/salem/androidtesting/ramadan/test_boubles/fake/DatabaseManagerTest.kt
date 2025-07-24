package com.salem.androidtesting.ramadan.test_boubles.fake

import com.salem.androidtesting.ramadan_test.test_doubles.fake.DatabaseManager
import com.salem.androidtesting.ramadan_test.test_doubles.fake.InMemoryDatabase
import org.junit.Test
import kotlin.test.assertEquals

class DatabaseManagerTest {

    @Test
    fun `test fake test doubles scenario`(){
        val database = InMemoryDatabase()
        val databaseManager = DatabaseManager(database)

        databaseManager.save("Salem")
        val result =  databaseManager.get()
        assertEquals("Salem" , result)

    }
}


package com.salem.androidtesting.ramadan_test.test_doubles.fake

interface Database{
    fun save(str : String)
    fun get(): String
}


// Fake Impl
class InMemoryDatabase : Database{
    private var dataHolder  : String = ""
    override fun save(str: String) {
           dataHolder = str
    }

    override fun get(): String  = dataHolder

}

class DatabaseManager(private val database: Database) {
    fun save(str : String) = database.save(str)
    fun get() : String = database.get()
}


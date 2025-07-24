package com.salem.androidtesting.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.salem.androidtesting.philip.room.ShoppingDao
import com.salem.androidtesting.philip.room.ShoppingItem
import com.salem.androidtesting.philip.room.ShoppingItemDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.shoppingDao()
    }


    @After
    fun before() {
        database.close()
    }


    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem(
            name = "Mohamed",
            amount = 1,
            price = 1f,
            imageURL = "www.google.com",
            id = 1
        )
        dao.upsertShoppingItem(shoppingItem)

        val items = dao.observeAllShoppingItems().first()
        assertThat(items).contains(shoppingItem)
    }


    @Test
    fun deleteShoppingItem() = runTest {

        val shoppingItem = ShoppingItem(
            name = "Mohamed",
            amount = 1,
            price = 1f,
            imageURL = "www.google.com",
            id = 1
        )
        dao.upsertShoppingItem(shoppingItem)

        dao.deleteShoppingItems(shoppingItem)

        val items = dao.observeAllShoppingItems().first()

        assertThat(items).doesNotContain(shoppingItem)

    }

    @Test
    fun observeTotalPriceSum() = runTest {
        val shoppingItem1 = ShoppingItem(
            name = "Mohamed",
            amount = 1,
            price = 10f,
            imageURL = "www.google.com",
            id = 1
        )
        val shoppingItem2 =
            ShoppingItem(name = "Ali", amount = 2, price = 22f, imageURL = "www.google.com", id = 2)
        val shoppingItem3 = ShoppingItem(
            name = "Mohamed",
            amount = 3,
            price = 41f,
            imageURL = "www.google.com",
            id = 3
        )


        dao.upsertShoppingItem(shoppingItem1)
        dao.upsertShoppingItem(shoppingItem2)
        dao.upsertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrices().first()

        assertThat(totalPriceSum).isEqualTo( 1 * 10f  + 2 * 22f + 3 * 41f )



    }

}
package com.salem.androidtesting.philip.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItems( shoppingItem: ShoppingItem )

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems() : Flow<List<ShoppingItem>>


    @Query("SELECT SUM( price * amount ) FROM shopping_items" )
    fun observeTotalPrices() : Flow<Float>


}
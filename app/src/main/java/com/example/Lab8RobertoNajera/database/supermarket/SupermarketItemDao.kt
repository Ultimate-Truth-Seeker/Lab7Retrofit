package com.example.Lab8RobertoNajera.database.supermarket

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SupermarketItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: SupermarketItem)

    @Update
    suspend fun updateItem(item: SupermarketItem)

    @Delete
    suspend fun deleteItem(item: SupermarketItem)

    @Query("SELECT * FROM supermarket_items")
    fun getAllItems(): Flow<List<SupermarketItem>>

    @Query("SELECT * FROM supermarket_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): SupermarketItem?
}
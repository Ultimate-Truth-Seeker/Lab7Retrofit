package com.example.Lab8RobertoNajera.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.Lab8RobertoNajera.database.categories.CategoryDao
import com.example.Lab8RobertoNajera.database.categories.CategoryEntity
import com.example.Lab8RobertoNajera.database.supermarket.SupermarketItemDao
import com.example.Lab8RobertoNajera.database.supermarket.SupermarketItem

@Database(entities = [CategoryEntity::class, SupermarketItem::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun supermarketItemDao(): SupermarketItemDao

}
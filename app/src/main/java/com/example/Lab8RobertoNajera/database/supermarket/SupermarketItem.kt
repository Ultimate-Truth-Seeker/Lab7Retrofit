package com.example.Lab8RobertoNajera.database.supermarket

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supermarket_items")
data class SupermarketItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val itemName: String,
    val quantity: Int,
    val imagePath: String? = null // Ruta opcional para la imagen
)
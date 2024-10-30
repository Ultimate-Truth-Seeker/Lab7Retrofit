package com.example.Lab8RobertoNajera.ui.supermarket.repository

import com.example.Lab8RobertoNajera.database.supermarket.SupermarketItem
import com.example.Lab8RobertoNajera.database.supermarket.SupermarketItemDao
import kotlinx.coroutines.flow.Flow
import java.io.File

class SupermarketRepository(private val dao: SupermarketItemDao) {

    fun getAllItems(): Flow<List<SupermarketItem>> = dao.getAllItems()

    suspend fun insertItem(item: SupermarketItem) {
        dao.insertItem(item)
    }

    suspend fun updateItem(item: SupermarketItem) {
        dao.updateItem(item)
    }

    suspend fun deleteItem(item: SupermarketItem) {
        item.imagePath?.let { imagePath ->
            val file = File(imagePath)
            if (file.exists()) {
                file.delete() // Elimina la imagen del sistema de archivos
            }
        }
        dao.deleteItem(item) // Elimina el artículo de la base de datos
    }

    suspend fun getItemById(itemId: Int): SupermarketItem? {
        return dao.getItemById(itemId)
    }


}
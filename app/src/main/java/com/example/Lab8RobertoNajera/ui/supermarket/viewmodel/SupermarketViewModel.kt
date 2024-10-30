package com.example.Lab8RobertoNajera.ui.supermarket.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.Lab8RobertoNajera.database.supermarket.SupermarketItem
import com.example.Lab8RobertoNajera.ui.categories.repository.CategoriesRepository
import com.example.Lab8RobertoNajera.ui.categories.viewmodel.CategoriesViewModel
import com.example.Lab8RobertoNajera.ui.supermarket.repository.SupermarketRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SupermarketViewModel(private val repository: SupermarketRepository) : ViewModel() {

    val items: StateFlow<List<SupermarketItem>> = repository.getAllItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addItem(item: SupermarketItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    fun updateItem(item: SupermarketItem, newName: String, newQuantity: Int) {
        viewModelScope.launch {
            val updatedItem = item.copy(itemName = newName, quantity = newQuantity)
            repository.updateItem(updatedItem)
        }
    }

    fun deleteItem(item: SupermarketItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun addImagePathToItem(imagePath: String, itemId: Int) {
        viewModelScope.launch {
            val item = repository.getItemById(itemId)
            if (item != null) {
                val updatedItem = item.copy(imagePath = imagePath)
                repository.updateItem(updatedItem)
            }
        }
    }
}
class SupermarketViewModelFactory(private val repository: SupermarketRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupermarketRepository::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SupermarketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
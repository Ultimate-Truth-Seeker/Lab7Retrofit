package com.example.Lab8RobertoNajera.ui.meals.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Lab8RobertoNajera.networking.response.Meal
import com.example.Lab8RobertoNajera.ui.meals.repository.MealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealsViewModel(private val repository: MealsRepository = MealsRepository()) : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    fun fetchMeals(categoryName: String) {
        viewModelScope.launch {
            try {
                _meals.value = repository.getMealsByCategory(categoryName)
            } catch (e: Exception) {
                _meals.value = emptyList() // Manejo de errores
                e.printStackTrace()
            }
        }
    }
}
package com.example.lab7.ui.mealdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab7.networking.response.MealDetail
import com.example.lab7.ui.mealdetail.repository.MealDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealDetailViewModel(private val repository: MealDetailRepository = MealDetailRepository()) : ViewModel() {

    private val _mealDetail = MutableStateFlow<MealDetail?>(null)
    val mealDetail: StateFlow<MealDetail?> = _mealDetail

    fun fetchMealDetails(mealId: String) {
        viewModelScope.launch {
            try {
                _mealDetail.value = repository.getMealDetails(mealId)
            } catch (e: Exception) {
                _mealDetail.value = null // Manejo de errores
                e.printStackTrace()
            }
        }
    }
}
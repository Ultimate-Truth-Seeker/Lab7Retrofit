package com.example.lab7.ui.mealdetail.repository

import com.example.lab7.networking.MealsWebService
import com.example.lab7.networking.TheMealApi
import com.example.lab7.networking.response.MealDetail

class MealDetailRepository(private val api: TheMealApi = MealsWebService.api) {
    suspend fun getMealDetails(mealId: String): MealDetail? {
        return api.getMealDetails(mealId).meals.firstOrNull()
    }
}
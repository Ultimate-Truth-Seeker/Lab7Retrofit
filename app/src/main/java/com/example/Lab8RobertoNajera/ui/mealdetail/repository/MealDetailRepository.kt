package com.example.Lab8RobertoNajera.ui.mealdetail.repository

import com.example.Lab8RobertoNajera.networking.MealsWebService
import com.example.Lab8RobertoNajera.networking.TheMealApi
import com.example.Lab8RobertoNajera.networking.response.MealDetail

class MealDetailRepository(private val api: TheMealApi = MealsWebService.api) {
    suspend fun getMealDetails(mealId: String): MealDetail? {
        return api.getMealDetails(mealId).meals.firstOrNull()
    }
}
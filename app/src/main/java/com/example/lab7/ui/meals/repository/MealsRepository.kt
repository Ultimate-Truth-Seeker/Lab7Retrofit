package com.example.lab7.ui.meals.repository

import com.example.lab7.networking.MealsWebService
import com.example.lab7.networking.TheMealApi
import com.example.lab7.networking.response.Meal


class MealsRepository(private val api: TheMealApi = MealsWebService.api) {
    suspend fun getMealsByCategory(category: String): List<Meal> {
        return api.getMealsByCategory(category).meals
    }
}
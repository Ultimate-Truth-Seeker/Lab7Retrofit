package com.example.Lab8RobertoNajera.ui.meals.repository

import com.example.Lab8RobertoNajera.networking.MealsWebService
import com.example.Lab8RobertoNajera.networking.TheMealApi
import com.example.Lab8RobertoNajera.networking.response.Meal


class MealsRepository(private val api: TheMealApi = MealsWebService.api) {
    suspend fun getMealsByCategory(category: String): List<Meal> {
        return api.getMealsByCategory(category).meals
    }
}
package com.example.lab7.ui.categories.repository

import com.example.lab7.networking.MealsWebService
import com.example.lab7.networking.TheMealApi
import com.example.lab7.networking.response.Category

class CategoriesRepository(private val api: TheMealApi = MealsWebService.api) {
    suspend fun getCategories(): List<Category> {
        return api.getCategories().categories
    }
}
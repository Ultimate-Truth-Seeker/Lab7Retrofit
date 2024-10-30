package com.example.Lab8RobertoNajera.networking

import com.example.Lab8RobertoNajera.networking.response.CategoriesResponse
import com.example.Lab8RobertoNajera.networking.response.MealsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MealsWebService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: TheMealApi by lazy {
        retrofit.create(TheMealApi::class.java)
    }
    suspend fun getMealsCategories(): CategoriesResponse {
        return api.getCategories()
    }

    suspend fun filterMealsByCategory(category: String): MealsResponse {
        return api.getMealsByCategory(category)
    }
}
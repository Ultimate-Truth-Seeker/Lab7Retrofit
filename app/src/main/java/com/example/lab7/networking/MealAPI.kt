package com.example.lab7.networking

import com.example.lab7.networking.response.CategoriesResponse
import com.example.lab7.networking.response.MealDetailResponse
import com.example.lab7.networking.response.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealApi {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse
    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): MealDetailResponse

}
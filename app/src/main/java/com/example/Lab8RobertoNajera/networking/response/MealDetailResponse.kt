package com.example.Lab8RobertoNajera.networking.response

import com.google.gson.annotations.SerializedName

data class MealDetailResponse(
    @SerializedName("meals") val meals: List<MealDetail>
)

data class MealDetail(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strInstructions") val instructions: String,
    @SerializedName("strMealThumb") val thumbnail: String,
    @SerializedName("strCategory") val category: String,
    @SerializedName("strArea") val area: String
)

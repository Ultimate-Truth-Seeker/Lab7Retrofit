package com.example.lab7.networking.response

import com.google.gson.annotations.SerializedName

data class MealsResponse(
    @SerializedName("meals") val meals: List<Meal>
)

data class Meal(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val thumbnail: String
)
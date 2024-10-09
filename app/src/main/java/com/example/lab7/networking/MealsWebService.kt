package com.example.lab7.networking

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
}
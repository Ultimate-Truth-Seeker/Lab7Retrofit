package com.example.Lab8RobertoNajera.ui.categories.repository

import com.example.Lab8RobertoNajera.networking.MealsWebService
import com.example.Lab8RobertoNajera.networking.response.toEntity
import com.example.Lab8RobertoNajera.database.categories.CategoryDao
import com.example.Lab8RobertoNajera.database.categories.CategoryEntity

class CategoriesRepository(private val webService: MealsWebService,
                              private val mealCategoryDao: CategoryDao
) {
    suspend fun getMealsCategories(): List<CategoryEntity> {
        val entities = webService.getMealsCategories().categories
        val content = entities.map { it.toEntity() }
        mealCategoryDao.insertCategories(content)
        return mealCategoryDao.getAllCategories()
    }
}
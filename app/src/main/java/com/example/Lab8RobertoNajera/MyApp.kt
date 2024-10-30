package com.example.Lab8RobertoNajera

import android.app.Application
import androidx.room.Room
import com.example.Lab8RobertoNajera.networking.MealsWebService
import com.example.Lab8RobertoNajera.database.AppDatabase
import com.example.Lab8RobertoNajera.ui.categories.repository.CategoriesRepository
import com.example.Lab8RobertoNajera.ui.supermarket.repository.SupermarketRepository

class MyApp : Application() {

    // Singleton instance of the Room database
    private lateinit var database: AppDatabase
        private set

    lateinit var categoryRepository: CategoriesRepository
        private set

    lateinit var supermarketRepository: SupermarketRepository
        private set

    lateinit var categoryWebService: MealsWebService
        private set

    override fun onCreate() {
        super.onCreate()

        categoryWebService = MealsWebService
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "meal-categories-db"
        ).fallbackToDestructiveMigration().build()

        categoryRepository = CategoriesRepository(
            categoryWebService,
            database.categoryDao()
        )

        supermarketRepository = SupermarketRepository(
            database.supermarketItemDao()
        )
    }
}
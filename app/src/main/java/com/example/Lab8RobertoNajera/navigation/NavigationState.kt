package com.example.Lab8RobertoNajera.navigation

sealed class NavigationState(val route: String) {
    data object MealsCategories: NavigationState("categories")

    data object MealsList: NavigationState("meals/{categoryName}")  {
        fun createRoute(category: String) = "meals/$category"
    }
    data object MealDetails: NavigationState("mealDetail/{mealId}") {
        fun createRoute(meal: String) = "mealDetail/$meal"
    }
}
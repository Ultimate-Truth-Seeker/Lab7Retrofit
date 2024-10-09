package com.example.lab7.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab7.ui.mealdetail.view.MealDetailScreen
import com.example.lab7.ui.categories.view.CategoriesScreen
import com.example.lab7.ui.meals.view.MealsScreen

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = NavigationState.MealsCategories.route,
        modifier = modifier) {
        composable(route = NavigationState.MealsCategories.route) {
            CategoriesScreen(navController) {categoryName ->
                navController.navigate(NavigationState.MealsList.createRoute(categoryName))
            }
        }
        composable(
            route = NavigationState.MealsList.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName")
            MealsScreen(categoryName = categoryName ?: "", navController, onMealClick = { mealId ->
                navController.navigate(NavigationState.MealDetails.createRoute(mealId))
            })
        }
        composable(
            route = NavigationState.MealDetails.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")
            if (mealId != null) {
                MealDetailScreen(mealId = mealId, navController)
            }
        }
    }
}


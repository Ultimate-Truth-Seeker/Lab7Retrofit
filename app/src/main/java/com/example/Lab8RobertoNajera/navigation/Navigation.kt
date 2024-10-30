package com.example.Lab8RobertoNajera.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.Lab8RobertoNajera.ui.mealdetail.view.MealDetailScreen
import com.example.Lab8RobertoNajera.ui.categories.view.CategoriesScreen
import com.example.Lab8RobertoNajera.ui.categories.viewmodel.CategoriesViewModel
import com.example.Lab8RobertoNajera.ui.meals.view.MealsScreen
import com.example.Lab8RobertoNajera.ui.supermarket.view.SupermarketScreen
import com.example.Lab8RobertoNajera.ui.supermarket.viewmodel.SupermarketViewModel

@Composable
fun Navigation(navController: NavHostController, mealViewModel: CategoriesViewModel, supermarketViewModel: SupermarketViewModel, modifier: Modifier = Modifier) {

    NavHost(navController = navController,
        startDestination = NavigationState.MealsCategories.route,
        modifier = modifier) {
        composable(route = NavigationState.MealsCategories.route) {
            CategoriesScreen(navController, mealViewModel) {categoryName ->
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
        composable(route = "supermarket") {
            SupermarketScreen(supermarketViewModel)
        }
    }
}


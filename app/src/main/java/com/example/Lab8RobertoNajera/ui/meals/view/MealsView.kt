package com.example.Lab8RobertoNajera.ui.meals.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.Lab8RobertoNajera.navigation.AppBar
import com.example.Lab8RobertoNajera.networking.response.Meal
import com.example.Lab8RobertoNajera.ui.meals.viewmodel.MealsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsScreen(categoryName: String,navController: NavController, onMealClick: (String) -> Unit) {
    val viewModel: MealsViewModel = viewModel()
    val meals by viewModel.meals.collectAsState()

    // Llamar a fetchMeals solo si no se han cargado las recetas aún
    LaunchedEffect(categoryName) {
        viewModel.fetchMeals(categoryName)
    }

    Scaffold(
        topBar = {
            AppBar("Recetas de $categoryName", navController)
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(meals) { meal ->
                MealItem(meal = meal, onClick = {
                    onMealClick(meal.id)
                })
            }
        }
    }
}

@Composable
fun MealItem(meal: Meal, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(model = meal.thumbnail),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = meal.name, modifier = Modifier.padding(16.dp))
        }
    }
}
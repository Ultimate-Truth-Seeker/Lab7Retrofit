package com.example.lab7.ui.categories.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lab7.navigation.AppBar
import com.example.lab7.networking.response.Category
import com.example.lab7.ui.categories.viewmodel.CategoriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavHostController, onCategoryClick: (String) -> Unit) {
    val viewModel: CategoriesViewModel = viewModel()
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            AppBar("Categorías", navController)
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(categories) { category ->
                CategoryItem(category = category, onClick = {
                    onCategoryClick(category.name)
                })
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(16.dp)
        )
    }
}
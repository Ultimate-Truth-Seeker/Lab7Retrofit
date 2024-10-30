package com.example.Lab8RobertoNajera.ui.categories.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.Lab8RobertoNajera.navigation.AppBar
import com.example.Lab8RobertoNajera.database.categories.CategoryEntity
import com.example.Lab8RobertoNajera.ui.categories.viewmodel.CategoriesViewModel
import com.example.Lab8RobertoNajera.ui.supermarket.view.SupermarketScreen
import com.example.Lab8RobertoNajera.ui.supermarket.viewmodel.SupermarketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavHostController, viewModel: CategoriesViewModel, onCategoryClick: (String) -> Unit) {
    val categories = viewModel.categories.observeAsState(initial = emptyList())
    val isLoading = viewModel.isLoading.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    errorMessage?.let {
        Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
    }
    Scaffold(
        topBar = {
            AppBar("Categorías", navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    items(categories.value) { category ->
                        CategoryItem(category) { onCategoryClick(category.name) }
                    }
                    item {
                        CategoryItem(CategoryEntity("", "Supermarket", "", "")) {navController.navigate("supermarket") }
                    }

                }


            }
        }

    }
}

@Composable
fun CategoryItem(category: CategoryEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(data = category.thumbnailUrl),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = category.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = category.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
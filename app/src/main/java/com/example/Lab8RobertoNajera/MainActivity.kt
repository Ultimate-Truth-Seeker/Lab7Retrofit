package com.example.Lab8RobertoNajera

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.Lab8RobertoNajera.navigation.Navigation
import com.example.Lab8RobertoNajera.ui.categories.viewmodel.CategoriesViewModel
import com.example.Lab8RobertoNajera.ui.categories.viewmodel.MealViewModelFactory
import com.example.Lab8RobertoNajera.ui.supermarket.view.SupermarketScreen
import com.example.Lab8RobertoNajera.ui.supermarket.viewmodel.SupermarketViewModel
import com.example.Lab8RobertoNajera.ui.supermarket.viewmodel.SupermarketViewModelFactory
import com.example.Lab8RobertoNajera.ui.theme.Lab7Theme

class MainActivity : ComponentActivity() {
    private lateinit var mealViewModel: CategoriesViewModel
    private lateinit var supermarketViewModel: SupermarketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mealRepository = (applicationContext as MyApp).categoryRepository
        val supermarketRepository = (applicationContext as MyApp).supermarketRepository
        mealViewModel = ViewModelProvider(
            this,
            MealViewModelFactory(mealRepository))[CategoriesViewModel::class.java]
        supermarketViewModel = SupermarketViewModel(supermarketRepository)


        enableEdgeToEdge()
        setContent {
            Lab7Theme {
                Navigation(rememberNavController(), mealViewModel, supermarketViewModel)
                //SupermarketScreen(supermarketViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab7Theme {
        Greeting("Android")
    }
}
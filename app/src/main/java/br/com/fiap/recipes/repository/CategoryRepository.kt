package br.com.fiap.recipes.repository


import androidx.compose.ui.graphics.Color
import br.com.fiap.recipes.R
import br.com.fiap.recipes.model.Category

fun getAllCategories() = listOf<Category>(
    Category(
        id = 1000,
        name = "Chicken",
        image = R.drawable.chicken,
        background = Color(0xFFABF2E9)
    ),

    Category(
        id = 2000,
        name = "Beef",
        image = R.drawable.beef,
        background = Color(0xFFF4D6C0)
    ),

    Category(
        id = 3000,
        name = "Fish",
        image = R.drawable.fish,
        background = Color(0xFFC6DAFA)
    ),

    Category(
        id = 4000,
        name = "Bakery",
        image = R.drawable.bakery,
        background = Color(0xFFF8D9D9)
    ),

    Category(
        id = 5000,
        name = "Vegetable",
        image = R.drawable.vegetable,
        background = Color(0xFFABF2E9)
    ),

    Category(id = 6000,
        name = "Desserts",
        image = R.drawable.dessert,
        background = Color(0xFF72412B)
    ),

    Category(id = 7000,
        name = "Drinks",
        image = R.drawable.drink,
        background = Color(0xFF80DEEA)
    )
)

fun getCategoryById(id: Int) = getAllCategories()
    .find { category ->
        category.id == id
    }




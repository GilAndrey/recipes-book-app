package br.com.fiap.recipes.repository

import br.com.fiap.recipes.R
import br.com.fiap.recipes.model.Category
import br.com.fiap.recipes.model.DifficultLevel
import br.com.fiap.recipes.model.Recipe
import br.com.fiap.recipes.model.User
import java.time.LocalDate

fun getAllRecipes() = listOf<Recipe>(
    Recipe(
        id = 1,
        category = Category(id = 6000, name = "Desserts"),
        user = User(id = 100, name = "Ana Maria"),
        difficultLevel = DifficultLevel.BEGINNER,
        name = "Bolo de Cenoura",
        description = "Moist, spiced, carrot-filled cake with tangy cream cheese frosting.",
        cookingTime = 60,
        createdAt = LocalDate.now(),
        image = R.drawable.bolo_cenoura
    ),
    Recipe(
        id = 2,
        category = Category(id = 5000, name = "Vegetables"),
        user = User(id = 200, name = "Pedro Augusto"),
        difficultLevel = DifficultLevel.INTERMEDIATE,
        name = "Salada de Palmito",
        description = "Refreshing heart of palm salad, light, savory, and subtly sweet.",
        cookingTime = 10,
        createdAt = LocalDate.now(),
        image = R.drawable.salada_de_palmito
    ),
    Recipe(
        id = 3,
        category = Category(id = 4000, name = "Bakery"),
        user = User(id = 300, name = "Patricia Oliveira"),
        difficultLevel = DifficultLevel.ADVANCED,
        name = "Pão de calabresa",
        description = "Spicy sausage and cheese bread: soft, savory, delicious.",
        cookingTime = 10,
        createdAt = LocalDate.now(),
        image = R.drawable.pao_calabresa
    ),
    Recipe (
        id = 4,
        category = Category(id = 5000, name = "Vegetables"),
        user = User(id = 400, name = "Gil Andrey"),
        difficultLevel = DifficultLevel.ADVANCED,
        name = "Sopa de legumes",
        description = "Hearty vegetable soup: warm, nourishing, fresh, wholsome",
        cookingTime = 45,
        createdAt = LocalDate.now(),
        image = R.drawable.sopa_legumes
    ),
    Recipe(
        id = 5,
        category = Category(id = 2000, name = "Beef"),
        user = User(id = 500, name = "Josh Green"),
        difficultLevel = DifficultLevel.ADVANCED,
        name = "Feijoada",
        description = "Rich, smoky, hearty, bean and meat stew",
        cookingTime = 120,
        createdAt = LocalDate.now(),
        image = R.drawable.feijoada
    )
)

fun getRecipesByCategory(id: Int) = getAllRecipes()
    .filter { recipe ->
        recipe.category.id == id
    }

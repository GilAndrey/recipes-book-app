package br.com.fiap.recipes.repository


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fiap.recipes.factory.RetrofitClient
import br.com.fiap.recipes.model.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun getAllCategories(): List<Category> {

    var categories by remember {
        mutableStateOf(listOf<Category>())
    }

    // Usamos o LaunchedEffect para que a requisição seja feita apenas UMA VEZ
    // quando a tela abrir, evitando o loop infinito que causava falta de memória.
    LaunchedEffect(Unit) {
        val callCategories = RetrofitClient.getCategoryService().getAllCategories()

        callCategories.enqueue(object : Callback<List<Category>> {

            // Executado quando a requisição recebe uma resposta do servidor (sucesso ou erro).
            override fun onResponse(
                call: Call<List<Category>?>,
                response: Response<List<Category>?>
            ) {
                if (response.isSuccessful) {
                    categories = response.body() ?: emptyList()
                }
            }

            // Executado quando ocorre uma falha na comunicação (ex: sem internet ou servidor fora do ar).
            override fun onFailure(
                call: Call<List<Category>?>,
                p1: Throwable
            ) {
                p1.printStackTrace()
            }

        })
    }

    return categories

}



//fun getAllCategories() = listOf<Category>(
//    Category(
//        id = 1000,
//        name = "Chicken",
//        image = R.drawable.chicken,
//        background = Color(0xFFABF2E9)
//    ),
//
//    Category(
//        id = 2000,
//        name = "Beef",
//        image = R.drawable.beef,
//        background = Color(0xFFF4D6C0)
//    ),
//
//    Category(
//        id = 3000,
//        name = "Fish",
//        image = R.drawable.fish,
//        background = Color(0xFFC6DAFA)
//    ),
//
//    Category(
//        id = 4000,
//        name = "Bakery",
//        image = R.drawable.bakery,
//        background = Color(0xFFF8D9D9)
//    ),
//
//    Category(
//        id = 5000,
//        name = "Vegetable",
//        image = R.drawable.vegetable,
//        background = Color(0xFFABF2E9)
//    ),
//
//    Category(id = 6000,
//        name = "Desserts",
//        image = R.drawable.dessert,
//        background = Color(0xFF72412B)
//    ),
//
//    Category(id = 7000,
//        name = "Drinks",
//        image = R.drawable.drink,
//        background = Color(0xFF80DEEA)
//    )
//)

@Composable
fun getCategoryById(id: Int) = getAllCategories()
    .find { category ->
        category.id == id
    }




package br.com.fiap.recipes.factory

import br.com.fiap.recipes.service.CategoryService
import br.com.fiap.recipes.service.RecipeService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {

    // http://localhost:8080/api/categories/2 ->
    // isso vai olhar na maquina ospedeira
    const val BASE_URL = "http://10.0.2.2:8080/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getCategoryService(): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    fun getRecipeService(): RecipeService {
        return retrofit.create(RecipeService::class.java)
    }

}
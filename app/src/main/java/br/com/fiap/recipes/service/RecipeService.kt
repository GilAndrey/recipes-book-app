package br.com.fiap.recipes.service

import retrofit2.Call
import br.com.fiap.recipes.model.Recipe
import br.com.fiap.recipes.model.RecipeRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {

    //http://localhost:8080/api/recipes/categories/7

    /*
     * O objeto Call encapsula uma requisição HTTP que será enviada para o servidor.
     * Ele permite que o Retrofit execute a chamada de forma assíncrona (em segundo plano)
     * e converta a resposta JSON recebida em uma lista de objetos do tipo Recipe.
     */
    @GET("recipes/categories/{categoryId}")
    fun getRecipesByCategory(@Path("categoryId") id: Int): Call<List<Recipe>>

    @GET("recipes/recents")
    fun getLatestRecipes(): Call<List<Recipe>>

    @POST("recipes")
    fun saveRecipe(@Body recipeRequest: RecipeRequest): Call<RecipeRequest>


}
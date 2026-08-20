package br.com.fiap.recipes.service

import android.adservices.adid.AdId
import br.com.fiap.recipes.model.Category
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {

    /*
    * Call é utilizada para representar uma requisição HTTP assinctrona
    * */
    @GET("categories")
    fun getAllCategories(): Call<List<Category>>

    @GET("categories/{categoryId}")
    fun getCategoryById(@Path("categoryId") categoryId: Int): Call<Category>

}
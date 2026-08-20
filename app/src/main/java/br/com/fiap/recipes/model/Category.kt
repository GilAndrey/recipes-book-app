package br.com.fiap.recipes.model

import br.com.fiap.recipes.R
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("cateogryId") val id : Int = 0,
    @SerializedName("categoryName") val name : String = "Name",
    @SerializedName("url") val image: String = "",
    @SerializedName("color") val background: String = "FFFFFFFF"
)
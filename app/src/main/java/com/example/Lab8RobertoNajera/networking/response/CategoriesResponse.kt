package com.example.Lab8RobertoNajera.networking.response

import com.example.Lab8RobertoNajera.database.categories.CategoryEntity
import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories") val categories: List<Category>
)
data class Category(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val thumbnail: String,
    @SerializedName("strCategoryDescription") val description: String
)

fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        name = this.name ?: "",
        thumbnailUrl = this.thumbnail,
        description = this.description
    )
}
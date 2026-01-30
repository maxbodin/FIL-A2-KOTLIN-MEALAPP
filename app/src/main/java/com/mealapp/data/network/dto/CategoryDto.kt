package com.mealapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String,
    @SerializedName("strCategoryThumb") val thumbnail: String,
    @SerializedName("strCategoryDescription") val description: String
)
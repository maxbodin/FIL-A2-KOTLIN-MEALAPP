package com.mealapp.data.network.dto.response

import com.google.gson.annotations.SerializedName

data class CategoriesResponse(
    @SerializedName("categories") val categories: List<CategoryDto>
)
package com.mealapp.data.network.dto.response

import com.google.gson.annotations.SerializedName
import com.mealapp.data.network.dto.CategoryDto

data class CategoriesResponse(
    @SerializedName("categories") val categories: List<CategoryDto>
)
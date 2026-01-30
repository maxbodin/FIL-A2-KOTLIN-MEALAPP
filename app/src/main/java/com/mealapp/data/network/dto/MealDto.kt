package com.mealapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class MealDto(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val thumbnail: String
)
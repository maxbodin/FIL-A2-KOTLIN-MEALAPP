package com.mealapp.data.network.dto.response

import com.google.gson.annotations.SerializedName
import com.mealapp.data.network.dto.MealDto

data class MealsResponse(
    @SerializedName("meals") val meals: List<MealDto>
)
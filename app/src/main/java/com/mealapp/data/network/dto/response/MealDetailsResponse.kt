package com.mealapp.data.network.dto.response

import com.google.gson.annotations.SerializedName
import com.mealapp.data.network.dto.MealDetailsDto

data class MealDetailsResponse(
    @SerializedName("meals") val meals: List<MealDetailsDto>
)
package com.mealapp.ui.details

import com.mealapp.domain.model.MealDetails

data class MealDetailsState(
    val isLoading: Boolean = false,
    val mealDetails: MealDetails? = null,
    val error: String? = null
)
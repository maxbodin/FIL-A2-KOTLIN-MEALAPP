package com.mealapp.ui.meals

import com.mealapp.domain.model.Meal

data class MealsState(
    val isLoading: Boolean = false,
    val meals: List<Meal>? = null,
    val error: String? = null,
    val categoryName: String = ""
)
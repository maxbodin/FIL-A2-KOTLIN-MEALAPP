package com.mealapp.ui.meals

import com.mealapp.domain.model.Meal

data class MealsState(
    val isLoading: Boolean = false,
    val meals: List<Meal> = emptyList(),
    val error: String? = null,
    val categoryName: String = "",
    val searchQuery: String = "",
    val isAscending: Boolean = true
)
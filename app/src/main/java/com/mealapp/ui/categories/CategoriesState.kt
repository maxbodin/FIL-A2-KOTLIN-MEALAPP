package com.mealapp.ui.categories

import com.mealapp.domain.model.Category

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val isAscending: Boolean = true,
    val randomMealId: String? = null
)
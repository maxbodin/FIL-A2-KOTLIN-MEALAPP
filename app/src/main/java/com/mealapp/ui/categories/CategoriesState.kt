package com.mealapp.ui.categories

import com.mealapp.domain.model.Category

data class CategoriesState(
    val isLoading: Boolean = false,
    val categories: List<Category>? = null,
    val error: String? = null
)
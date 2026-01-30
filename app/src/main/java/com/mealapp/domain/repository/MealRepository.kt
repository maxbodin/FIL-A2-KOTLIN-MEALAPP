package com.mealapp.domain.repository

import com.mealapp.domain.model.Category
import com.mealapp.domain.model.Meal
import com.mealapp.domain.model.MealDetails
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getCategories(): Flow<Result<List<Category>>>
    fun getMealsByCategory(category: String): Flow<Result<List<Meal>>>
    fun getMealDetails(id: String): Flow<Result<MealDetails?>>
}
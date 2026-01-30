package com.mealapp.data.repository

import com.mealapp.data.network.ApiClient
import com.mealapp.data.network.TheMealDbApiService
import com.mealapp.data.network.toDomain
import com.mealapp.domain.model.Category
import com.mealapp.domain.model.Meal
import com.mealapp.domain.model.MealDetails
import com.mealapp.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


class MealRepositoryImpl(
    private val apiService: TheMealDbApiService = ApiClient.mealDbApi
) : MealRepository {

    override fun getCategories(): Flow<Result<List<Category>>> = flow {
        val response = apiService.getCategories().categories

        emit(Result.success(response.map { it.toDomain() }))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }

    override fun getMealsByCategory(category: String): Flow<Result<List<Meal>>> = flow {
        val response = apiService.getMealsByCategory(category).meals

        emit(Result.success(response.map { it.toDomain() }))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }

    override fun getMealDetails(id: String): Flow<Result<MealDetails?>> = flow {
        val response = apiService.getMealDetails(id).meals.firstOrNull()

        emit(Result.success(response?.toDomain()))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }

    override fun getRandomMeal(): Flow<Result<MealDetails?>> = flow {
        val response = apiService.getRandomMeal().meals.firstOrNull()

        emit(Result.success(response?.toDomain()))
    }.catch { throwable ->
        emit(Result.failure(throwable))
    }
}
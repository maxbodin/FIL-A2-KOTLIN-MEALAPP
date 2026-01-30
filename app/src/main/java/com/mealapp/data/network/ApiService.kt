package com.mealapp.data.network

import com.mealapp.data.network.dto.response.CategoriesResponse
import com.mealapp.data.network.dto.response.MealDetailsResponse
import com.mealapp.data.network.dto.response.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface TheMealDbApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealDetailsResponse
}
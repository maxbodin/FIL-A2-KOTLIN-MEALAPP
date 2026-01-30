package com.mealapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mealapp.data.network.ApiClient
import com.mealapp.data.repository.MealRepositoryImpl
import com.mealapp.domain.repository.MealRepository

class ViewModelFactory(private val repository: MealRepository = MealRepositoryImpl(ApiClient.create())) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoriesViewModel::class.java) -> {
                CategoriesViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
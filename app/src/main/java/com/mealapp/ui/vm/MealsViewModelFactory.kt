package com.mealapp.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mealapp.data.repository.MealRepositoryImpl
import com.mealapp.ui.meals.MealsViewModel

class MealsViewModelFactory(private val repository: MealRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealsViewModel(repository, SavedStateHandle()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.mealapp.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mealapp.data.repository.MealRepositoryImpl

class MealDetailsViewModelFactory(private val repository: MealRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealDetailsViewModel(repository, SavedStateHandle()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
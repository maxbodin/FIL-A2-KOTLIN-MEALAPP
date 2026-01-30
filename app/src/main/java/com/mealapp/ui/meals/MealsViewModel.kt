package com.mealapp.ui.meals

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mealapp.domain.repository.MealRepository
import com.mealapp.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MealsViewModel(
    repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MealsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Routes.ARG_CATEGORY_NAME)?.let { categoryName ->
            _state.value = MealsState(categoryName = categoryName)
            getMeals(repository, categoryName)
        }
    }

    private fun getMeals(repository: MealRepository, categoryName: String) {
        _state.value = _state.value.copy(isLoading = true)
        repository.getMealsByCategory(categoryName).onEach { result ->
            result.onSuccess { meals ->
                _state.value = _state.value.copy(isLoading = false, meals = meals, error = null)
            }.onFailure { throwable ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = throwable.message ?: "An unknown error occurred"
                )
            }
        }.launchIn(viewModelScope)
    }
}
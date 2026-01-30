package com.mealapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mealapp.domain.repository.MealRepository
import com.mealapp.ui.meals.MealsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MealsViewModel(private val repository: MealRepository) : ViewModel() {
    private val _state = MutableStateFlow(MealsState())
    val state = _state.asStateFlow()

    fun loadMeals(category: String) {
        if (_state.value.meals != null) return
        _state.value = _state.value.copy(isLoading = true, categoryName = category)
        repository.getMealsByCategory(category).onEach { result ->
            result.onSuccess { _state.value = _state.value.copy(isLoading = false, meals = it) }
            result.onFailure {
                _state.value = _state.value.copy(isLoading = false, error = it.message)
            }
        }.launchIn(viewModelScope)
    }
}
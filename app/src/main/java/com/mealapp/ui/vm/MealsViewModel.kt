package com.mealapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mealapp.domain.model.Meal
import com.mealapp.domain.repository.MealRepository
import com.mealapp.ui.meals.MealsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MealsViewModel(private val repository: MealRepository) : ViewModel() {
    private val _state = MutableStateFlow(MealsState())
    val state = _state.asStateFlow()

    private var allMeals: List<Meal> = emptyList()

    fun loadMeals(category: String) {
        if (allMeals.isNotEmpty()) return

        _state.value = _state.value.copy(isLoading = true, categoryName = category)

        repository.getMealsByCategory(category).onEach { result ->
            result.onSuccess { meals ->
                allMeals = meals
                updateDisplayList()
            }
            result.onFailure {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = it.message ?: "Failed to load meals"
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        updateDisplayList()
    }

    fun toggleSortOrder() {
        _state.value = _state.value.copy(isAscending = !_state.value.isAscending)
        updateDisplayList()
    }

    private fun updateDisplayList() {
        val filtered = allMeals.filter {
            it.name.contains(_state.value.searchQuery, ignoreCase = true)
        }

        val sorted = if (_state.value.isAscending) {
            filtered.sortedBy { it.name }
        } else {
            filtered.sortedByDescending { it.name }
        }

        _state.value = _state.value.copy(
            isLoading = false,
            meals = sorted
        )
    }
}
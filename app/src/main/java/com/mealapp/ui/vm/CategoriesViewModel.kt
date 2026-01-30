package com.mealapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mealapp.domain.model.Category
import com.mealapp.domain.repository.MealRepository
import com.mealapp.ui.categories.CategoriesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesViewModel(private val repository: MealRepository) : ViewModel() {
    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    private var allCategories: List<Category> = emptyList()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        _state.value = _state.value.copy(isLoading = true)
        repository.getCategories().onEach { result ->
            result.onSuccess {
                allCategories = it
                updateDisplayList()
            }.onFailure {
                _state.value = _state.value.copy(isLoading = false, error = it.message)
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
        val filtered = allCategories.filter {
            it.name.contains(_state.value.searchQuery, ignoreCase = true)
        }
        val sorted = if (_state.value.isAscending) {
            filtered.sortedBy { it.name }
        } else {
            filtered.sortedByDescending { it.name }
        }
        _state.value = _state.value.copy(isLoading = false, categories = sorted)
    }

    fun onRandomMealClick() {
        _state.value = _state.value.copy(isLoading = true)

        repository.getRandomMeal().onEach { result ->
            result.onSuccess { mealDetails ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    randomMealId = mealDetails?.id
                )
            }
            result.onFailure {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to fetch random meal"
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onNavigationConsumed() {
        _state.value = _state.value.copy(randomMealId = null)
    }
}
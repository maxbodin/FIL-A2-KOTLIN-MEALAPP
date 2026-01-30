package com.mealapp.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mealapp.domain.repository.MealRepository
import com.mealapp.ui.categories.CategoriesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesViewModel(
    private val repository: MealRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        // Set loading to true
        _state.value = _state.value.copy(isLoading = true)

        // Collect the Flow from the repository
        repository.getCategories().onEach { result ->
            result.onSuccess { categoryList ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    categories = categoryList,
                    error = null
                )
            }.onFailure { throwable ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = throwable.message ?: "An unexpected error occurred"
                )
            }
        }.launchIn(viewModelScope)
    }
}
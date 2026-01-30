package com.mealapp.ui.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mealapp.domain.repository.MealRepository
import com.mealapp.ui.details.MealDetailsState
import com.mealapp.ui.navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MealDetailsViewModel(
    repository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MealDetailsState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>(Routes.ARG_MEAL_ID)?.let { mealId ->
            getMealDetails(repository, mealId)
        }
    }

    private fun getMealDetails(repository: MealRepository, mealId: String) {
        _state.value = MealDetailsState(isLoading = true)
        repository.getMealDetails(mealId).onEach { result ->
            result.onSuccess { details ->
                _state.value = MealDetailsState(isLoading = false, mealDetails = details)
            }.onFailure { throwable ->
                _state.value = MealDetailsState(
                    isLoading = false,
                    error = throwable.message ?: "An unknown error occurred"
                )
            }
        }.launchIn(viewModelScope)
    }
}
package com.mealapp.ui.meals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mealapp.ui.components.SearchAndSortBar
import com.mealapp.ui.components.shimmerEffect
import com.mealapp.ui.vm.MealsViewModel
import com.mealapp.ui.vm.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsScreen(
    categoryName: String,
    onMealClick: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: MealsViewModel = viewModel(factory = ViewModelFactory())
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(categoryName) { viewModel.loadMeals(categoryName) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        categoryName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchAndSortBar(
                query = state.searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                isAscending = state.isAscending,
                onSortToggle = viewModel::toggleSortOrder
            )

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (state.isLoading) {
                    LazyColumn {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(110.dp)
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }

                state.error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                state.meals.let { meals ->
                    if (meals.isEmpty() && !state.isLoading) {
                        Text("No recipes found for '${state.searchQuery}'")
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 16.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(meals, key = { it.id }) { meal ->
                                MealItem(meal = meal, onClick = { onMealClick(meal.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}
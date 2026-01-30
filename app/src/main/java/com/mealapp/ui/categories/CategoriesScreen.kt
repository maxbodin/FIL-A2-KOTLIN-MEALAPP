package com.mealapp.ui.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mealapp.ui.components.SearchAndSortBar
import com.mealapp.ui.vm.CategoriesViewModel
import com.mealapp.ui.vm.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    onRandomMealFound: (String) -> Unit,
    viewModel: CategoriesViewModel = viewModel(factory = ViewModelFactory())
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.randomMealId) {
        state.randomMealId?.let { id ->
            onRandomMealFound(id)
            viewModel.onNavigationConsumed()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "MealApp",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.onRandomMealClick() },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = androidx.compose.foundation.shape.CircleShape
            ) {
                Text(
                    text = "Surprise Me!",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SearchAndSortBar(
                query = state.searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                isAscending = state.isAscending,
                onSortToggle = viewModel::toggleSortOrder
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                }

                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                state.categories.let { categories ->
                    if (categories.isEmpty() && !state.isLoading) {
                        Text(
                            text = "No categories found.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    } else {
                        CategoryList(
                            categories = categories,
                            onCategoryClick = onCategoryClick
                        )
                    }
                }
            }
        }
    }
}
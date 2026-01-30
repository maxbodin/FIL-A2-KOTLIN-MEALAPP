package com.mealapp.ui.categories

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mealapp.ui.vm.CategoriesViewModel
import com.mealapp.ui.vm.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    onCategoryClick: (categoryName: String) -> Unit,
    viewModel: CategoriesViewModel = viewModel(factory = ViewModelFactory())
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MealApp Categories") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            }

            state.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            state.categories?.let { categories ->
                if (categories.isEmpty() && !state.isLoading) {
                    Text(text = "No categories found.")
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
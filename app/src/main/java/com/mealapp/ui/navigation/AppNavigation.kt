package com.mealapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mealapp.ui.categories.CategoriesScreen
import com.mealapp.ui.details.MealDetailScreen
import com.mealapp.ui.meals.MealsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CATEGORIES_LIST
    ) {

        composable(route = Routes.CATEGORIES_LIST) {
            CategoriesScreen(
                onCategoryClick = { categoryName ->
                    navController.navigate(Routes.mealsListRoute(categoryName))
                }
            )
        }
        composable(
            route = Routes.MEALS_LIST,
            arguments = listOf(navArgument(Routes.ARG_CATEGORY_NAME) {
                type = NavType.StringType
            })
        ) { _ ->
            MealsScreen(
                onMealClick = { mealId ->
                    navController.navigate(Routes.mealDetailsRoute(mealId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.MEAL_DETAILS,
            arguments = listOf(navArgument(Routes.ARG_MEAL_ID) {
                type = NavType.StringType
            })
        ) { _ ->
            MealDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
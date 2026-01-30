package com.mealapp.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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

        composable(
            route = Routes.CATEGORIES_LIST,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            }) {
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
            }),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            }
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString(Routes.ARG_CATEGORY_NAME) ?: ""

            MealsScreen(
                categoryName = categoryName,
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
            }),
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))
            }
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString(Routes.ARG_MEAL_ID) ?: ""

            MealDetailScreen(
                mealId = mealId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
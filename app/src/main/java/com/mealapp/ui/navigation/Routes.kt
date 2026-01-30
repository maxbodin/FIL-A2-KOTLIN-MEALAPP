package com.mealapp.ui.navigation

object Routes {
    const val CATEGORIES_LIST = "categories_list"

    const val MEALS_LIST = "meals_list/{categoryName}"
    const val ARG_CATEGORY_NAME = "categoryName"
    fun mealsListRoute(categoryName: String) = "meals_list/$categoryName"

    const val MEAL_DETAILS = "meal_details/{mealId}"
    const val ARG_MEAL_ID = "mealId"
    fun mealDetailsRoute(mealId: String) = "meal_details/$mealId"
}
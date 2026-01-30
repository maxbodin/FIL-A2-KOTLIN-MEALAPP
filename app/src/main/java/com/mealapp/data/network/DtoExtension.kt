package com.mealapp.data.network

import com.mealapp.data.network.dto.CategoryDto
import com.mealapp.data.network.dto.MealDetailsDto
import com.mealapp.data.network.dto.MealDto
import com.mealapp.domain.model.Category
import com.mealapp.domain.model.Ingredient
import com.mealapp.domain.model.Meal
import com.mealapp.domain.model.MealDetails

fun CategoryDto.toDomain(): Category {
    return Category(
        id = this.id,
        name = this.name,
        thumbnail = this.thumbnail
    )
}

fun MealDto.toDomain(): Meal {
    return Meal(
        id = this.id,
        name = this.name,
        thumbnail = this.thumbnail
    )
}

fun MealDetailsDto.toDomain(): MealDetails {
    val ingredientsList = mutableListOf<Ingredient>()

    val ingredientPairs = listOf(
        strIngredient1 to strMeasure1, strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3, strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5, strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7, strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9, strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11, strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13, strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15, strIngredient16 to strMeasure16,
        strIngredient17 to strMeasure17, strIngredient18 to strMeasure18,
        strIngredient19 to strMeasure19, strIngredient20 to strMeasure20
    )

    ingredientPairs.forEach { (ingredient, measure) ->
        if (!ingredient.isNullOrBlank()) {
            ingredientsList.add(Ingredient(name = ingredient, measure = measure ?: ""))
        }
    }

    return MealDetails(
        id = id, name = name, category = category, area = area,
        instructions = instructions, thumbnail = thumbnail,
        tags = tags?.split(",")?.map { it.trim() } ?: emptyList(),
        youtubeUrl = youtubeUrl, ingredients = ingredientsList
    )
}
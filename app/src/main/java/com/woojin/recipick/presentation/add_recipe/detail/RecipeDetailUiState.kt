package com.woojin.recipick.presentation.add_recipe.detail

import com.woojin.recipick.data.local.entity.RecipeEntity

data class RecipeDetailUiState(
    val isEditMode: Boolean = false,
    val recipeEntity: RecipeEntity = RecipeEntity(null, "", emptyList(), emptyList()),
    val editTitle: String = recipeEntity.title,
//    val editIngredients: List<String> = recipeEntity.ingredients,
//    val editSteps: List<String> = recipeEntity.steps,
)

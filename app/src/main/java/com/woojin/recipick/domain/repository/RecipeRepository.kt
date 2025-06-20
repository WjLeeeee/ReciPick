package com.woojin.recipick.domain.repository

import com.woojin.recipick.data.local.entity.RecipeEntity

interface RecipeRepository {
    suspend fun getRecipe(recipeId: Int): RecipeEntity
    suspend fun updateRecipe(data: RecipeEntity)
}
package com.woojin.recipick.data.local.impl

import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {
    override suspend fun getRecipe(
        recipeId: Int
    ): RecipeEntity {
        return recipeDao.getRecipe(recipeId)
    }
}
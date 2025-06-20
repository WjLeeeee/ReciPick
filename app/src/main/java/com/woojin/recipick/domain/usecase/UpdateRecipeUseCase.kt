package com.woojin.recipick.domain.usecase

import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.repository.RecipeRepository
import javax.inject.Inject

class UpdateRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        data: RecipeEntity
    ) {
        recipeRepository.updateRecipe(
            data = data
        )
    }
}
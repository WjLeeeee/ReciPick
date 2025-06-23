package com.woojin.recipick.domain.usecase

import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.repository.RecipeRepository
import javax.inject.Inject

/** id로 레시피 상세 데이터 가져 오는 UseCase */
class GetRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        recipeId: Int
    ): RecipeEntity {
        return recipeRepository.getRecipe(recipeId)
    }
}
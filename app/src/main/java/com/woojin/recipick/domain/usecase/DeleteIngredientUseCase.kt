package com.woojin.recipick.domain.usecase

import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.repository.RecipeRepository
import javax.inject.Inject

/** 레시피 수정 중 재료 삭제 후 저장 해주는 UseCase */
class DeleteIngredientUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        index: Int,
        data: RecipeEntity
    ): RecipeEntity {
        return recipeRepository.deleteIngredient(
            index = index,
            data = data
        )
    }
}
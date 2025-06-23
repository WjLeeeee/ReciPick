package com.woojin.recipick.domain.usecase

import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.repository.RecipeRepository
import javax.inject.Inject

/** 레시피 수정 중 조리 과정 삭제 후 저장 해주는 UseCase */
class DeleteStepUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        index: Int,
        data: RecipeEntity
    ): RecipeEntity {
        return recipeRepository.deleteStep(
            index = index,
            data = data
        )
    }
}
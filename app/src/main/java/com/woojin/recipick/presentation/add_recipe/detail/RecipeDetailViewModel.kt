package com.woojin.recipick.presentation.add_recipe.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val recipeDao: RecipeDao
) : ViewModel() {

    private val _recipeDetailData =
        MutableStateFlow(RecipeEntity(null, "", emptyList(), emptyList()))
    val recipeDetailData: StateFlow<RecipeEntity> = _recipeDetailData.asStateFlow()

    /** 레시피 상세 */
    fun recipeDetail(recipeId: Int?) {
        viewModelScope.launch {
            recipeId?.let { id ->
                recipeDao.getRecipe(id).let { data ->
                    _recipeDetailData.value =
                        RecipeEntity(data.id, data.title, data.ingredients, data.steps)
                }
            }
        }
    }

    /** 수정된 레시피 저장 */
    fun updateRecipe(data: RecipeEntity) {
        viewModelScope.launch {
            recipeDao.updateRecipe(data)
            _recipeDetailData.value = data
        }
    }

    /** 레시피 수정 중 재료 삭제 */
    fun deleteIngredient(index: Int) {
        viewModelScope.launch {
            val currentRecipeIngredients = _recipeDetailData.value
            //index 가 정상 인지, 리스트 크기 보다 크지 않은지 확인
            if (index >= 0 && index < currentRecipeIngredients.ingredients.size) {
                //현재 재료 리스트 에서 해당 index 재료 제거 후 저장
                val updatedIngredients = currentRecipeIngredients.ingredients.toMutableList()
                updatedIngredients.removeAt(index)
                val newRecipeData = currentRecipeIngredients.copy(
                    ingredients = updatedIngredients.toList()
                )
                //새롭게 저장된 리스트 적용
                _recipeDetailData.value = newRecipeData
                recipeDao.updateRecipe(newRecipeData)
            }
        }
    }

    /** 레시피 수정 중 조리 과정 삭제 */
    fun deleteSteps(index: Int) {
        viewModelScope.launch {
            val currentRecipeSteps = _recipeDetailData.value
            //index 가 정상 인지, 리스트 크기 보다 크지 않은지 확인
            if (index >= 0 && index < currentRecipeSteps.steps.size) {
                //현재 재료 리스트 에서 해당 index 재료 제거 후 저장
                val updatedSteps = currentRecipeSteps.steps.toMutableList()
                updatedSteps.removeAt(index)
                val newRecipeData = currentRecipeSteps.copy(
                    steps = updatedSteps.toList()
                )
                //새롭게 저장된 리스트 적용
                _recipeDetailData.value = newRecipeData
                recipeDao.updateRecipe(newRecipeData)
            }
        }
    }
}
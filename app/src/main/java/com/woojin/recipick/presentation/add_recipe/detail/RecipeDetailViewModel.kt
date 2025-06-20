package com.woojin.recipick.presentation.add_recipe.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.domain.usecase.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    private val recipeDao: RecipeDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeDetailUiState())
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    /** 레시피 상세 */
    fun recipeDetail(recipeId: Int?) {
        viewModelScope.launch {
            recipeId?.let { id ->
                val data = getRecipeUseCase(id)
                _uiState.update {
                    it.copy(
                        recipeEntity = data,
                        editTitle = data.title,
                        editIngredients = data.ingredients,
                        editSteps = data.steps
                    )
                }
            }
        }
    }

    /** 수정된 레시피 저장 */
    fun updateRecipe(data: RecipeEntity) {
        viewModelScope.launch {
            recipeDao.updateRecipe(data)
            _uiState.update {
                it.copy(
                    recipeEntity = data,
                    editTitle = data.title,
                    editIngredients = data.ingredients,
                    editSteps = data.steps
                )
            }
        }
    }

    /** 레시피 수정 중 재료 삭제 */
    fun deleteIngredient(index: Int) {
        viewModelScope.launch {
            val currentRecipeIngredients = _uiState.value.recipeEntity
            //index 가 정상 인지, 리스트 크기 보다 크지 않은지 확인
            if (index >= 0 && index < currentRecipeIngredients.ingredients.size) {
                //현재 재료 리스트 에서 해당 index 재료 제거 후 저장
                val updatedIngredients = currentRecipeIngredients.ingredients.toMutableList()
                updatedIngredients.removeAt(index)
                val newRecipeData = currentRecipeIngredients.copy(
                    ingredients = updatedIngredients.toList()
                )
                //새롭게 저장된 리스트 적용
                _uiState.update {
                    it.copy(
                        recipeEntity = newRecipeData,
                        editIngredients = newRecipeData.ingredients
                    )
                }
                recipeDao.updateRecipe(newRecipeData)
            }
        }
    }

    /** 레시피 수정 중 조리 과정 삭제 */
    fun deleteSteps(index: Int) {
        viewModelScope.launch {
            val currentRecipeSteps = _uiState.value.recipeEntity
            //index 가 정상 인지, 리스트 크기 보다 크지 않은지 확인
            if (index >= 0 && index < currentRecipeSteps.steps.size) {
                //현재 재료 리스트 에서 해당 index 재료 제거 후 저장
                val updatedSteps = currentRecipeSteps.steps.toMutableList()
                updatedSteps.removeAt(index)
                val newRecipeData = currentRecipeSteps.copy(
                    steps = updatedSteps.toList()
                )
                //새롭게 저장된 리스트 적용
                _uiState.update {
                    it.copy(
                        recipeEntity = newRecipeData,
                        editSteps = newRecipeData.steps
                    )
                }
                recipeDao.updateRecipe(newRecipeData)
            }
        }
    }

    /** TopAppBar 뒤로 가기 클릭 시 editMode false 설정 */
    fun updateEditMode(mode: Boolean) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isEditMode = mode
                )
            }
        }
    }

    /** 레시피 타이틀 수정 update */
    fun updateEditTitle(updateTitle: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editTitle = updateTitle
                )
            }
        }
    }

    /** 레시피 재료 수정 update */
    fun updateEditIngredients(updateIngredients: List<String>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editIngredients = updateIngredients
                )
            }
        }
    }

    /** 레시피 과정 수정 update */
    fun updateEditSteps(updateSteps: List<String>) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    editSteps = updateSteps
                )
            }
        }
    }
}
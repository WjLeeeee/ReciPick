package com.woojin.recipick.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.presentation.add_recipe.state.RecipeInputState
import com.woojin.recipick.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val recipeDao: RecipeDao? //preView 때문에 nullable로 설정.
) : ViewModel() {
    private val _navigateToScreen = MutableStateFlow<Screen>(Screen.Main)
    val navigateToScreen: StateFlow<Screen> = _navigateToScreen.asStateFlow()

    private val _recipeInputState = mutableStateOf(RecipeInputState())
    val recipeInputState: State<RecipeInputState> = _recipeInputState

    fun navUpdate(value: Screen) {
        viewModelScope.launch {
            _navigateToScreen.emit(value)
        }
    }

    val recipes: StateFlow<List<RecipeEntity>> = recipeDao!!.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(0),
            initialValue = emptyList()
        )

    /** 레시피 제목 update */
    fun updateRecipeTitle(title: String) {
        _recipeInputState.value = _recipeInputState.value.copy(title = title)
        navUpdate(Screen.AddRecipeIngredients)
    }

    /** 레시피 재료 추가 */
    fun addIngredient(ingredient: String) {
        val currentIngredients = _recipeInputState.value.ingredients.toMutableList()
        if (currentIngredients.contains(ingredient)) {
            currentIngredients.remove(ingredient)
        } else {
            currentIngredients.add(ingredient)
        }
        _recipeInputState.value = _recipeInputState.value.copy(
            ingredients = currentIngredients.toList()
        )
    }

    /** 레시피 재료 목록 update */
    fun updateIngredients(ingredients: List<String>) {
        _recipeInputState.value = _recipeInputState.value.copy(ingredients = ingredients)
        navUpdate(Screen.AddRecipeSteps)
    }

    /** 조리 단계 update */
    fun updateRecipeSteps(steps: List<String>) {
        _recipeInputState.value = _recipeInputState.value.copy(steps = steps)
        saveRecipe()
    }

    /** 레시피 저장 */
    private fun saveRecipe() {
        val recipeEntity = RecipeEntity(
            id = null,
            title = _recipeInputState.value.title,
            ingredients = _recipeInputState.value.ingredients,
            steps = _recipeInputState.value.steps
        )
        viewModelScope.launch {
            try {
                recipeDao?.insert(recipeEntity)
                _recipeInputState.value = RecipeInputState()
                navUpdate(Screen.Main)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /** 레시피 제거 */
    fun deleteRecipe(recipeId: Int?) {
        viewModelScope.launch {
            recipeId?.let { id ->
                recipeDao?.delete(id)
            }
        }
    }
}
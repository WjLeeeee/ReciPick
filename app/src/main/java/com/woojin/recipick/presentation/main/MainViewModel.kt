package com.woojin.recipick.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.presentation.add_recipe.state.RecipeInputState
import com.woojin.recipick.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _navigateToScreen = MutableStateFlow<Screen>(Screen.Main)
    val navigateToScreen: StateFlow<Screen> = _navigateToScreen.asStateFlow()

    private val _recipeInputState = mutableStateOf(RecipeInputState())
    val recipeInputState: State<RecipeInputState> = _recipeInputState

    fun navUpdate(value: Screen) {
        viewModelScope.launch {
            _navigateToScreen.emit(value)
        }
    }

    /** 레시피 제목 update */
    fun updateRecipeTitle(title: String) {
        _recipeInputState.value = _recipeInputState.value.copy(title = title)
        navUpdate(Screen.AddRecipeIngredients)
    }
    
    fun getRecipeTitle(): String {
        return _recipeInputState.value.title
    }

    fun getIngredients(): List<String> {
        return _recipeInputState.value.ingredients
    }

    fun getSteps(): List<String> {
        return _recipeInputState.value.steps
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
        navUpdate(Screen.Main)
    }

    /** 레시피 저장 */
    fun saveRecipe() {
        //상태 초기화, 아직 저장 로직은 구현 X
        _recipeInputState.value = RecipeInputState()
    }
}
package com.woojin.recipick.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woojin.recipick.presentation.RecipeInputState
import com.woojin.recipick.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _navigateToScreen = MutableStateFlow<Screen>(Screen.Main)
    val navigateToScreen: StateFlow<Screen> = _navigateToScreen.asStateFlow()

    private var recipeInputState by mutableStateOf(RecipeInputState())
        private set

    fun navUpdate(value: Screen) {
        viewModelScope.launch {
            _navigateToScreen.emit(value)
        }
    }

    /** 레시피 제목 update */
    fun updateRecipeTitle(title: String) {
        recipeInputState = recipeInputState.copy(title = title)
    }

    /** 레시피 재료 추가 */
    fun addIngredient(ingredient: String) {
        recipeInputState = recipeInputState.copy(
            ingredients = recipeInputState.ingredients + ingredient
        )
    }

    /** 레시피 재료 목록 update */
    fun updateIngredients(ingredients: List<String>) {
        recipeInputState = recipeInputState.copy(ingredients = ingredients)
    }

    /** 조리 단계 update */
    fun updateRecipeSteps(steps: String) {
        recipeInputState = recipeInputState.copy(steps = steps)
    }

    /** 레시피 저장 */
    fun saveRecipe() {
        //상태 초기화, 아직 저장 로직은 구현 X
        recipeInputState = RecipeInputState()
    }
}
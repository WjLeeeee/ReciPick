package com.woojin.recipick.presentation

sealed class Screen(val route: String) {
    object Main: Screen("main_screen")
    object AddRecipeTitle: Screen("add_recipe_title_screen")
    object AddRecipeIngredients: Screen("add_recipe_ingredients_screen")
    object AddRecipeSteps: Screen("add_recipe_steps_screen")
}
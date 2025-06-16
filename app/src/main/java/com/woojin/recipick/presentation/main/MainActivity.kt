package com.woojin.recipick.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.presentation.add_recipe.detail.RecipeDetailScreen
import com.woojin.recipick.presentation.add_recipe.detail.RecipeDetailViewModel
import com.woojin.recipick.presentation.add_recipe.ingredients.AddRecipeIngredientsScreen
import com.woojin.recipick.presentation.add_recipe.steps.AddRecipeStepsScreen
import com.woojin.recipick.presentation.add_recipe.title_and_ingredients.AddRecipeTitleAndIngredients
import com.woojin.recipick.presentation.navigation.Screen
import com.woojin.recipick.presentation.theme.RecipickTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val recipeDetailViewModel: RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipickTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = Unit) {
                    viewModel.navigateToScreen.collect { navigation ->
                        when (navigation) {
                            Screen.AddRecipeTitleAndIngredients -> navController.navigate(Screen.AddRecipeTitleAndIngredients.route)
                            Screen.AddRecipeIngredients -> navController.navigate(Screen.AddRecipeIngredients.route)
                            Screen.AddRecipeSteps -> navController.navigate(Screen.AddRecipeSteps.route)
                            Screen.Main -> navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Main.route) { //메인 화면 까지 스택 제거
                                    inclusive = true //메인 화면 자체도 스택 제거
                                }
                            }

                            Screen.RecipeDetail -> navController.navigate(Screen.RecipeDetail.route)
                        }
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {
                    composable(Screen.Main.route) {
                        AppScreen(
                            viewModel = viewModel,
                            onClick = { viewModel.navUpdate(Screen.AddRecipeTitleAndIngredients) },
                            mainItemClick = { recipeId ->
                                recipeDetailViewModel.recipeDetail(recipeId)
                                viewModel.navUpdate(Screen.RecipeDetail)
                            }
                        )
                    }

                    composable(Screen.AddRecipeTitleAndIngredients.route) {
                        val selectedIngredientName by viewModel.selectedIngredientName
                        AddRecipeTitleAndIngredients(
                            navController = navController,
                            selectedIngredientName = selectedIngredientName,
                            clearSelectedIngredient = { viewModel.clearSelectedIngredientName() },
                            addIngredientsClick = {
                                viewModel.navUpdate(Screen.AddRecipeIngredients)
                            },
                            onComplete = { data ->
                                viewModel.updateRecipeTitle(data.first)
                                viewModel.updateIngredients(data.second)
                                viewModel.navUpdate(Screen.AddRecipeSteps)
                            }
                        )
                    }

                    composable(Screen.AddRecipeIngredients.route) {
                        AddRecipeIngredientsScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable(Screen.AddRecipeSteps.route) {
                        AddRecipeStepsScreen(
                            navController = navController,
                            onClick = { viewModel.updateRecipeSteps(it) }
                        )
                    }

                    composable(Screen.RecipeDetail.route) {
                        RecipeDetailScreen(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}
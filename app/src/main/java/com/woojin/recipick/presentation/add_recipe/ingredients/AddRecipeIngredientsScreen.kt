package com.woojin.recipick.presentation.add_recipe.ingredients

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.data.local.datasource.FoodCategories
import com.woojin.recipick.presentation.main.MainViewModel
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun AddRecipeIngredientsScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
    onClick: (List<String>) -> Unit
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.add_recipe_title),
                true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            TitleAndRowItems(
                title = "육류",
                items = FoodCategories.meats,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            TitleAndRowItems(
                title = "채소류",
                items = FoodCategories.vegetables,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            TitleAndRowItems(
                title = "버섯",
                items = FoodCategories.mushRooms,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            TitleAndRowItems(
                title = "유제품",
                items = FoodCategories.dairies,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            TitleAndRowItems(
                title = "양념",
                items = FoodCategories.seasonings,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            TitleAndRowItems(
                title = "곡류",
                items = FoodCategories.grains,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            TitleAndRowItems(
                title = "기타",
                items = FoodCategories.etc,
                selectedIngredients = viewModel.recipeInputState.value.ingredients,
                onIngredientClick = { viewModel.addIngredient(it) }
            )
            Button(
                modifier = Modifier.padding(start = 16.dp),
                onClick = { onClick(viewModel.recipeInputState.value.ingredients) }
            ) {
                Text("다음")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeIngredientsScreenPreview() {
    RecipickTheme {
        AddRecipeIngredientsScreen(
            navController = rememberNavController(),
            onClick = {}
        )
    }
}
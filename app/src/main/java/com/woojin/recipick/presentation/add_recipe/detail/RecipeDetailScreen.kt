package com.woojin.recipick.presentation.add_recipe.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.data.local.entity.RecipeEntity
import com.woojin.recipick.presentation.main.components.AlertNoTitleFunc
import com.woojin.recipick.presentation.main.components.FloatingButton
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun RecipeDetailScreen(
    navController: NavHostController,
    recipeDetailViewModel: RecipeDetailViewModel
) {
    val recipeDetailData by recipeDetailViewModel.uiState.collectAsState()
    RecipeDetail(
        navController = navController,
        recipeDetailUiState = recipeDetailData,
        saveRecipeBtn = { data ->
            recipeDetailViewModel.updateRecipe(data = data)
        },
        deleteIngredient = { index ->
            recipeDetailViewModel.deleteIngredient(index = index)
        },
        deleteStep = { index ->
            recipeDetailViewModel.deleteStep(index = index)
        },
        isEditMode = recipeDetailData.isEditMode,
        updateEditMode = { recipeDetailViewModel.updateEditMode(it) },
        editTitle = recipeDetailData.editTitle,
        updateEditTitle = { recipeDetailViewModel.updateEditTitle(it) },
        editIngredients = recipeDetailData.editIngredients,
        updateEditIngredients = { recipeDetailViewModel.updateEditIngredients(it) },
        editSteps = recipeDetailData.editSteps,
        updateEditSteps = { recipeDetailViewModel.updateEditSteps(it) },
        deleteIngredientDialog = recipeDetailData.showDeleteIngredientDialog,
        deleteIngredientIndex = recipeDetailData.deleteIngredientIndex,
        updateDeleteIngredientDialog = { recipeDetailViewModel.updateDeleteIngredientDialog(it) },
        deleteStepDialog = recipeDetailData.showDeleteStepDialog,
        deleteStepIndex = recipeDetailData.deleteStepIndex,
        updateDeleteStepDialog = { recipeDetailViewModel.updateDeleteStepDialog(it) },
    )
}

@Composable
fun RecipeDetail(
    navController: NavHostController,
    recipeDetailUiState: RecipeDetailUiState,
    saveRecipeBtn: (RecipeEntity) -> Unit,
    deleteIngredient: (Int) -> Unit,
    deleteStep: (Int) -> Unit,
    isEditMode: Boolean,
    updateEditMode: (Boolean) -> Unit,
    editTitle: String,
    updateEditTitle: (String) -> Unit,
    editIngredients: List<String>,
    updateEditIngredients: (List<String>) -> Unit,
    editSteps: List<String>,
    updateEditSteps: (List<String>) -> Unit,
    deleteIngredientDialog: Boolean,
    deleteIngredientIndex: Int,
    updateDeleteIngredientDialog: (Pair<Boolean, Int>) -> Unit,
    deleteStepDialog: Boolean,
    deleteStepIndex: Int,
    updateDeleteStepDialog: (Pair<Boolean, Int>) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.recipe_detail_title),
                true,
                onBackClick = {
                    updateEditMode(false)
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    if (isEditMode) {
                        saveRecipeBtn(
                            RecipeEntity(
                                recipeDetailUiState.recipeEntity.id,
                                editTitle,
                                editIngredients,
                                editSteps
                            )
                        )
                    }
                    updateEditMode(!isEditMode)
                },
                iconString = if (isEditMode) "save" else "edit"
            )
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                // 레시피 제목
                item {
                    if (isEditMode) {
                        OutlinedTextField(
                            value = editTitle,
                            onValueChange = { updateEditTitle(it) },
                            label = { Text(stringResource(R.string.recipe_title_edit_text)) },
                            singleLine = true,
                        )
                    } else {
                        Text(
                            text = recipeDetailUiState.recipeEntity.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                // 재료 아이템
                item {
                    RecipeDetailListItem(
                        titleResID = R.string.ingredients_section_title,
                        isEditMode = isEditMode,
                        items = editSteps,
                        updateDeleteItemDialog = { index ->
                            updateDeleteStepDialog(Pair(true, index))
                        },
                        updateEditItems = { updateEditSteps(it) },
                        onAddItem = {
                            val newList = editSteps.toMutableList()
                            newList.add("") // 빈 문자열 추가 또는 "새 재료" 등 기본값
                            updateEditSteps(newList)
                        },
                        addButtonTextResId = R.string.add_recipe_step_button,
                        emptyListMessageResId = R.string.no_ingredients_message,
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // 조리 단계 아이템
                item {
                    RecipeDetailListItem(
                        titleResID = R.string.steps_section_title,
                        isEditMode = isEditMode,
                        items = editIngredients,
                        updateDeleteItemDialog = { index ->
                            updateDeleteIngredientDialog(Pair(true, index))
                        },
                        updateEditItems = { updateEditIngredients(it) },
                        onAddItem = {
                            val newList = editIngredients.toMutableList()
                            newList.add("") // 빈 문자열 추가 또는 "새 재료" 등 기본값
                            updateEditIngredients(newList)
                        },
                        addButtonTextResId = R.string.add_recipe,
                        emptyListMessageResId = R.string.no_steps_message,
                    )
                }
            }

            when {
                deleteIngredientDialog && deleteIngredientIndex != -1 -> {
                    AlertNoTitleFunc(
                        onDismissRequest = { updateDeleteIngredientDialog(Pair(false, -1)) },
                        onConfirmation = {
                            deleteIngredient(deleteIngredientIndex)
                            updateDeleteIngredientDialog(Pair(false, -1))
                        },
                        dialogText = R.string.check_delete_item
                    )
                }

                deleteStepDialog && deleteStepIndex != -1 -> {
                    AlertNoTitleFunc(
                        onDismissRequest = { updateDeleteStepDialog(Pair(false, -1)) },
                        onConfirmation = {
                            deleteStep(deleteStepIndex)
                            updateDeleteStepDialog(Pair(false, -1))
                        },
                        dialogText = R.string.check_delete_item
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreview() {
    RecipickTheme {
        RecipeDetail(
            navController = rememberNavController(),
            recipeDetailUiState = RecipeDetailUiState(
                recipeEntity = RecipeEntity(
                    1,
                    "레시피제목",
                    listOf("양파1개", "대파1개"),
                    listOf("재료넣고", "볶기")
                )
            ),
            saveRecipeBtn = {},
            deleteIngredient = { _ -> },
            deleteStep = { _ -> },
            isEditMode = true,
            updateEditMode = {},
            editTitle = "레시피 제목 수정중",
            updateEditTitle = {},
            editIngredients = listOf("양파1개 수정중이지롱", "대파1개"),
            updateEditIngredients = {},
            editSteps = listOf("재료넣고 수정하자 수정수정", "볶기"),
            updateEditSteps = {},
            deleteIngredientDialog = false,
            deleteIngredientIndex = -1,
            updateDeleteIngredientDialog = {},
            deleteStepDialog = false,
            deleteStepIndex = -1,
            updateDeleteStepDialog = {}
        )
    }
}
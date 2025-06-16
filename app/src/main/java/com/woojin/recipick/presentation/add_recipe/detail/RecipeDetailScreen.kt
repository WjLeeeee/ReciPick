package com.woojin.recipick.presentation.add_recipe.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.woojin.recipick.presentation.add_recipe.steps.RecipeStep
import com.woojin.recipick.presentation.main.components.AlertNoTitleFunc
import com.woojin.recipick.presentation.main.components.FloatingButton
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun RecipeDetailScreen(
    navController: NavHostController,
    detailItem: RecipeEntity,
    saveRecipeBtn: (RecipeEntity) -> Unit,
    deleteIngredient: (Int) -> Unit,
    deleteSteps: (Int) -> Unit
) {
    var isEditMode by remember { mutableStateOf(false) }
    var editTitle by remember(
        detailItem.title,
        isEditMode
    ) { mutableStateOf(detailItem.title) }
    var editIngredients by remember(
        detailItem.ingredients,
        isEditMode
    ) { mutableStateOf(detailItem.ingredients) }
    var editSteps by remember(
        detailItem.steps,
        isEditMode
    ) { mutableStateOf(detailItem.steps) }

    var showDeleteIngredientDialog by remember { mutableStateOf(false) } //재료 삭제 dialog 표시 여부
    var showDeleteStepsDialog by remember { mutableStateOf(false) } // 단계 삭제 dialog 표시 여부

    var deleteIngredientIndex by remember { mutableStateOf(-1) } // 삭제 재료 인덱스 저장
    var deleteStepsIndex by remember { mutableStateOf(-1) } // 삭제 단계 인덱스 저장


    LaunchedEffect(isEditMode, detailItem) {
        if (isEditMode) {
            editTitle = detailItem.title
            editIngredients = detailItem.ingredients.toMutableList()
            editSteps = detailItem.steps.toMutableList()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.recipe_detail_title),
                true,
                onBackClick = {
                    isEditMode = false
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingButton(
                onClick = {
                    isEditMode = !isEditMode
                    if (!isEditMode) {
                        saveRecipeBtn(
                            RecipeEntity(detailItem.id, editTitle, editIngredients, editSteps)
                        )
                    }
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
                            onValueChange = { editTitle = it },
                            label = { Text(stringResource(R.string.recipe_title_edit_text)) },
                            singleLine = true,
                        )
                    } else {
                        Text(
                            text = detailItem.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }

                // 재료 섹션 제목
                item {
                    Text(
                        text = stringResource(R.string.ingredients_section_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                //재료 목록 수정
                if (isEditMode) {
                    itemsIndexed(editIngredients) { index, ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    deleteIngredientIndex = index
                                    showDeleteIngredientDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "삭제 아이콘",
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .size(20.dp),
                                )
                            }
                            TextField(
                                value = ingredient,
                                onValueChange = { newValue ->
                                    val newList = editIngredients.toMutableList()
                                    newList[index] = newValue
                                    editIngredients = newList
                                },
                                label = { Text("${stringResource(R.string.ingredient_label)} ${index + 1}") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    item {
                        // 재료 추가 버튼
                        OutlinedButton(
                            onClick = {
                                val newList = editIngredients.toMutableList()
                                newList.add("") // 빈 문자열 추가 또는 "새 재료" 등 기본값
                                editIngredients = newList
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "추가 버튼")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.add_recipe))
                        }
                    }
                } else {
                    if (detailItem.ingredients.isNotEmpty()) {
                        items(detailItem.ingredients) { ingredient ->
                            Text(
                                text = "- $ingredient",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = stringResource(R.string.no_ingredients_message),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                            )
                        }
                    }
                }

                // 간격
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                // 조리 단계 섹션 제목
                item {
                    Text(
                        text = stringResource(R.string.steps_section_title), // "조리 단계"
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (isEditMode) {
                    //조리 단계 수정
                    itemsIndexed(editSteps) { index, step ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    showDeleteStepsDialog = true
                                    deleteStepsIndex = index
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = "삭제 아이콘",
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .size(20.dp),
                                )
                            }
                            TextField(
                                value = step,
                                onValueChange = { newValue ->
                                    val newList = editSteps.toMutableList()
                                    newList[index] = newValue
                                    editSteps = newList
                                },
                                label = { Text("${stringResource(R.string.step_label)} ${index + 1}") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    item {
                        // 조리 과정 추가 버튼
                        OutlinedButton(
                            onClick = {
                                val newList = editSteps.toMutableList()
                                newList.add("") // 빈 문자열 추가 또는 "새 재료" 등 기본값
                                editSteps = newList
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.Add, contentDescription = "추가 버튼")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.add_recipe_step_button))
                        }
                    }
                } else {
                    // 조리 단계 목록
                    if (detailItem.steps.isNotEmpty()) {
                        items(detailItem.steps.size) { index -> // 단계 번호와 함께 표시
                            Text(
                                text = "${index + 1}. ${detailItem.steps[index]}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                            )
                        }
                    }
                }
            }

            when {
                showDeleteIngredientDialog && deleteIngredientIndex != -1 -> {
                    AlertNoTitleFunc(
                        onDismissRequest = {
                            showDeleteIngredientDialog = false
                        },
                        onConfirmation = {
                            deleteIngredient(deleteIngredientIndex)
                            deleteIngredientIndex = -1
                            showDeleteIngredientDialog = false
                        },
                        dialogText = R.string.check_delete_item
                    )
                }

                showDeleteStepsDialog && deleteStepsIndex !=  -1 -> {
                    AlertNoTitleFunc(
                        onDismissRequest = {
                            showDeleteStepsDialog = false
                        },
                        onConfirmation = {
                            deleteSteps(deleteStepsIndex)
                            deleteStepsIndex = -1
                            showDeleteStepsDialog = false
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
fun RecipeDetailScreenPreview() {
    RecipickTheme {
        RecipeDetailScreen(
            navController = rememberNavController(),
            detailItem = RecipeEntity(1, "레시피제목", listOf("양파1개", "대파1개"), listOf("재료넣고", "볶기")),
            saveRecipeBtn = {},
            deleteIngredient = { _ -> },
            deleteSteps = { _ -> }
        )
    }
}
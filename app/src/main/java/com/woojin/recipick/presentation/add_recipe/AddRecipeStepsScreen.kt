package com.woojin.recipick.presentation.add_recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.add_recipe.components.StepInputField
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun AddRecipeStepsScreen(
    navController: NavHostController,
    onClick: () -> Unit
) {
    val steps = remember {
        mutableStateListOf(
            RecipeStep(1, ""),
            RecipeStep(2, ""),
            RecipeStep(3, ""),
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.add_recipe_title),
                true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            // 스크롤 가능한 조리 과정 목록
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                itemsIndexed(steps, key = { _, step -> step.id }) { index, step ->
                    StepInputField(
                        stepNumber = index + 1,
                        text = step.description,
                        onTextChange = { newDescription ->
                            // 해당 스텝의 설명을 업데이트
                            steps[index] = step.copy(description = newDescription)
                        },
                        onDeleteClick = {
                            if (steps.size > 1) {
                                steps.remove(step)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    // 조리 과정 추가 버튼
                    OutlinedButton(
                        onClick = {
                            val newId = (steps.lastOrNull()?.id ?: 0) + 1
                            steps.add(RecipeStep(newId, ""))
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_recipe_step_button))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.add_recipe_step_button))
                    }
                }
                item {
                    Button(
                        onClick = { onClick() }
                    ) {
                        Text(stringResource(R.string.save_button))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeStepsScreenPreview() {
    RecipickTheme {
        AddRecipeStepsScreen(
            navController = rememberNavController(),
            onClick = {}
        )
    }
}

data class RecipeStep(
    val id: Int,
    var description: String
)
package com.woojin.recipick.presentation.add_recipe.detail

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.data.local.dao.RecipeDao
import com.woojin.recipick.presentation.main.MainViewModel
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun RecipeDetailScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val recipeDetail = viewModel.recipeDetailState.collectAsState()
    Log.d("woojinCheck", "detailData: $recipeDetail")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.recipe_detail_title),
                true,
                onBackClick = { navController.popBackStack() }
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
                    Text(
                        text = recipeDetail.value.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
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

                // 재료 목록
                if (recipeDetail.value.ingredients.isNotEmpty()) {
                    items(recipeDetail.value.ingredients) { ingredient ->
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

                // 조리 단계 목록
                if (recipeDetail.value.steps.isNotEmpty()) {
                    items(recipeDetail.value.steps.size) { index -> // 단계 번호와 함께 표시
                        Text(
                            text = "${index + 1}. ${recipeDetail.value.steps[index]}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 4.dp, start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
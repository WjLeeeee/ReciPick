package com.woojin.recipick.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.Screen
import com.woojin.recipick.presentation.theme.RecipickTheme
import com.woojin.recipick.presentation.theme.mainColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipickTheme {
                val navController = rememberNavController()
                LaunchedEffect(key1 = Unit) {
                    viewModel.addRecipeClick.collect {
                        navController.navigate(Screen.AddRecipeTitle.route)
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {
                    composable(Screen.Main.route) {
                        AppScreen(onClick = { viewModel.onAddRecipeClick() })
                    }

                    composable(Screen.AddRecipeTitle.route) {
                        AddRecipeTitleScreen(
                            navController = navController,
                            viewModel = viewModel
                        )
                        Text("ㅋㅋ 안녕")
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
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppScreen(onClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar(onClick) },
        floatingActionButton = {
            FloatingButton(onClick)
        }
    ) { innerPadding ->
        MainScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RecipickTheme {
        AppScreen(onClick = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.main_title)) },
        actions = {
            IconButton(onClick) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "검색")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mainColor
        )
    )
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("여기는 메인 화면")
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = mainColor
    ) {
        Icon(Icons.Filled.Add, "레시피 추가 버튼")
    }
}

@Composable
fun AddRecipeTitleScreen(navController: NavHostController, viewModel: MainViewModel) {
    Text("여기는 제목")
}

@Composable
fun AddRecipeIngredientsScreen(navController: NavHostController, viewModel: MainViewModel) {
    Text("여기는 재료 추가")
}

@Composable
fun AddRecipeStepsScreen(navController: NavHostController, viewModel: MainViewModel) {
    Text("여기는 조리 과정 설명")
}
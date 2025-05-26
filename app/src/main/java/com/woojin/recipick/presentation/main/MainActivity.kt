package com.woojin.recipick.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
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
                    viewModel.navigateToScreen.collect { navigation ->
                        when (navigation) {
                            Screen.AddRecipeTitle -> navController.navigate(Screen.AddRecipeTitle.route)
                            Screen.AddRecipeIngredients -> navController.navigate(Screen.AddRecipeIngredients.route)
                            Screen.AddRecipeSteps -> navController.navigate(Screen.AddRecipeSteps.route)
                            Screen.Main -> navController.navigate(Screen.Main.route) {
                                popUpTo(Screen.Main.route) { //메인 화면 까지 스택 제거
                                    inclusive = true //메인 화면 자체도 스택 제거
                                }
                            }
                        }
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = Screen.Main.route
                ) {
                    composable(Screen.Main.route) {
                        AppScreen(
                            onClick = { viewModel.navUpdate(Screen.AddRecipeTitle) }
                        )
                    }

                    composable(Screen.AddRecipeTitle.route) {
                        AddRecipeTitleScreen(
                            onClick = { viewModel.navUpdate(Screen.AddRecipeIngredients) }
                        )
                    }

                    composable(Screen.AddRecipeIngredients.route) {
                        AddRecipeIngredientsScreen(
                            onClick = { viewModel.navUpdate(Screen.AddRecipeSteps) }
                        )
                    }

                    composable(Screen.AddRecipeSteps.route) {
                        AddRecipeStepsScreen(
                            onClick = { viewModel.navUpdate(Screen.Main) }
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
fun AddRecipeTitleScreen(
    onClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("여기는 레시피 제목 화면")
            Button(
                onClick = { onClick() }
            ) {
                Text("다음")
            }
        }
    }
}

@Composable
fun AddRecipeIngredientsScreen(
    onClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("여기는 재료 추가 화면")
            Button(
                onClick = { onClick() }
            ) {
                Text("다음")
            }
        }
    }
}

@Composable
fun AddRecipeStepsScreen(
    onClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("여기는 조리 과정 설명")
            Button(
                onClick = { onClick() }
            ) {
                Text("완료")
            }
        }
    }
}
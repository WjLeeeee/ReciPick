package com.woojin.recipick.presentation.add_recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun AddRecipeTitleScreen(
    navController: NavHostController,
    onClick: () -> Unit
) {
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

@Preview(showBackground = true)
@Composable
fun AddRecipeTitleScreenPreview() {
    RecipickTheme {
        AddRecipeTitleScreen(
            navController = rememberNavController(),
            onClick = {}
        )
    }
}
package com.woojin.recipick.presentation.add_recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.presentation.theme.RecipickTheme

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

@Preview(showBackground = true)
@Composable
fun AddRecipeIngredientsScreenPreview() {
    RecipickTheme {
        AddRecipeIngredientsScreen(onClick = {})
    }
}
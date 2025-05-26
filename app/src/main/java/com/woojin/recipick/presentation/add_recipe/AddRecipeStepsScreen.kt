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

@Preview(showBackground = true)
@Composable
fun AddRecipeStepsScreenPreview() {
    RecipickTheme {
        AddRecipeStepsScreen(onClick = {})
    }
}
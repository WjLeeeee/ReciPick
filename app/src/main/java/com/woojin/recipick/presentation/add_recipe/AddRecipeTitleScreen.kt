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

@Preview(showBackground = true)
@Composable
fun AddRecipeTitleScreenPreview() {
    RecipickTheme {
        AddRecipeTitleScreen(onClick = {})
    }
}
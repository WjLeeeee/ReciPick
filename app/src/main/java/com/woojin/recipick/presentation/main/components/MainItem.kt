package com.woojin.recipick.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun MainItem(
    recipeTitle: String,
    onItemClick: () -> Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        OutlinedButton(
            onClick = { onItemClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(recipeTitle)
        }
        Text(
            text = recipeTitle,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainItemPreview() {
    RecipickTheme {
        MainItem(
            recipeTitle = "명란파스타",
            onItemClick = {}
        )
    }
}
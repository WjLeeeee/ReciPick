package com.woojin.recipick.presentation.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.woojin.recipick.presentation.theme.mainColor


@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = mainColor
    ) {
        Icon(Icons.Filled.Add, "레시피 추가 버튼")
    }
}

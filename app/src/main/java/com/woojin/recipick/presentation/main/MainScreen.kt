package com.woojin.recipick.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.presentation.main.components.FloatingButton
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

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

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("여기는 메인 화면")
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RecipickTheme {
        AppScreen(onClick = {})
    }
}
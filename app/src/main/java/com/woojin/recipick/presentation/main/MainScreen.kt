package com.woojin.recipick.presentation.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.main.components.FloatingButton
import com.woojin.recipick.presentation.main.components.MainItem
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun AppScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    onClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.main_title),
                false,
                onBackClick = {}
            )
        },
        floatingActionButton = {
            FloatingButton(onClick)
        }
    ) { innerPadding ->
        MainScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel
        )
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    Column(modifier = modifier.padding(16.dp)) {
        MainItem(viewModel.getRecipeTitle()) { }
        Button(
            onClick = {
                Toast.makeText(context, viewModel.getRecipeTitle(), Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("title 가져오기")
        }
        Button(
            onClick = {
                Toast.makeText(context, "${viewModel.getIngredients()}", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("ingredients 가져오기")
        }
        Button(
            onClick = {
                Toast.makeText(context, "${viewModel.getSteps()}", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text("조리 과정 가져오기")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RecipickTheme {
        AppScreen(
            navController = rememberNavController(),
            viewModel = MainViewModel(),
            onClick = {}
        )
    }
}
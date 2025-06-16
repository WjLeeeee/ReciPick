package com.woojin.recipick.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.R
import com.woojin.recipick.presentation.main.components.FloatingButton
import com.woojin.recipick.presentation.main.components.MainItem
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun AppScreen(
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
            FloatingButton(
                onClick = onClick,
                iconString = "add"
            )
        }
    ) { innerPadding ->
        MainScreen(
            modifier = Modifier.padding(innerPadding)
                .background(Color.White),
            viewModel = viewModel
        )
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val recipesState by viewModel.recipes.collectAsState() //저장된 레시피
    when {
        recipesState.isEmpty() -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.no_saved_recipes),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = recipesState,
                    key = { recipe -> recipe.id ?: -1 }
                ) { recipe ->
                    MainItem(
                        recipeTitle = recipe.title,
                        onItemClick = { viewModel.recipeDetail(recipe.id) },
                        onDeleteItemClick = { viewModel.deleteRecipe(recipe.id) }
                    )
                }
            }
        }
    }
}
package com.woojin.recipick.presentation.main.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.woojin.recipick.R
import com.woojin.recipick.presentation.theme.mainColor


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
package com.woojin.recipick

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.ui.theme.RecipickTheme
import com.woojin.recipick.ui.theme.mainColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipickTheme {
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    val context = LocalContext.current
    val addItemClick = {
        Toast.makeText(context, "레시피 추가", Toast.LENGTH_SHORT).show()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MyTopAppBar(addItemClick) },
        floatingActionButton = {
            FloatingButton(addItemClick)
        }
    ) { innerPadding ->
        MainScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    RecipickTheme {
        AppScreen()
    }
}

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

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("여기는 메인 화면")
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = mainColor
    ) {
        Icon(Icons.Filled.Add, "레시피 추가 버튼")
    }
}

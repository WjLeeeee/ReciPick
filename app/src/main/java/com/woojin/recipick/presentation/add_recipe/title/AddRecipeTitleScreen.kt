package com.woojin.recipick.presentation.add_recipe.title

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.main.components.MyTopAppBar
import com.woojin.recipick.presentation.theme.RecipickTheme

@Composable
fun AddRecipeTitleScreen(
    navController: NavHostController,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    var textState by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val handleSubmit = {
        btnOkClick(context, textState) { onClick(textState) }
        focusManager.clearFocus()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.add_recipe_title),
                true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = textState,
                    onValueChange = { newText ->
                        textState = newText
                    },
                    label = { Text(stringResource(R.string.recipe_title_text_hint)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default
                        .copy(imeAction = ImeAction.Done), //키보드 액션 버튼을 '완료' 로 설정
                    keyboardActions = KeyboardActions(
                        onDone = { handleSubmit() } //완료 버튼 클릭 시
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        btnOkClick(context, textState) { onClick(textState) }
                    }
                ) {
                    Text("다음")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddRecipeTitleScreenPreview() {
    RecipickTheme {
        AddRecipeTitleScreen(
            navController = rememberNavController(),
            onClick = {}
        )
    }
}

/** 다음 버튼 혹은 키보드 완료 버튼 클릭 시*/
private fun btnOkClick(context: Context, text: String, isTitleOk: () -> Unit) {
    if (text.trim().isNotEmpty()) {
        isTitleOk() //문제 없으면 콜백
    } else {
        Toast.makeText(context, (R.string.recipe_title_empty), Toast.LENGTH_SHORT).show()
    }
}
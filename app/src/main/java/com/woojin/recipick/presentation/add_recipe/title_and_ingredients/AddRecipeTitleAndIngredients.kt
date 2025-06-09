package com.woojin.recipick.presentation.add_recipe.title_and_ingredients

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.woojin.recipick.R
import com.woojin.recipick.presentation.main.MainViewModel
import com.woojin.recipick.presentation.main.components.MyTopAppBar

@Composable
fun AddRecipeTitleAndIngredients(
    navController: NavHostController,
    viewModel: MainViewModel,
    onComplete: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") } //레시피 제목

    var ingredientName by remember { mutableStateOf("") } //재료 이름
    var selectedUnit by remember { mutableStateOf("g") } //선택된 단위
    var quantityTotal by remember { mutableStateOf(0f) } //선택된 재료 양

    val addedIngredients = remember { mutableStateListOf<String>() } //추가된 재료

    val unitOptions = listOf("g", "스푼", "컵", "개")
    val unitQuantities = when (selectedUnit) {
        "g" -> listOf(100f, 200f, 600f)
        "스푼" -> listOf(0.3f, 0.5f, 1f)
        "컵" -> listOf(0.3f, 0.5f, 1f)
        "개" -> listOf(0.5f, 1f, 2f)
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.recipe_write),
                true,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 제목 입력
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.recipe_title_text)) },
                singleLine = true
            )

            HorizontalDivider()

            // 재료 이름
            OutlinedTextField(
                value = ingredientName,
                onValueChange = { ingredientName = it },
                label = { Text(stringResource(R.string.recipe_ingredient_text)) },
                singleLine = true
            )

            // 단위 선택
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stringResource(R.string.unit))
                unitOptions.forEach { unit ->
                    FilterChip(
                        selected = selectedUnit == unit,
                        onClick = {
                            selectedUnit = unit
                            quantityTotal = 0f
                        },
                        label = { Text(unit) }
                    )
                }
            }

            // 양 선택 버튼들
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                unitQuantities.forEach { q ->
                    Button(
                        onClick = {
                            if (ingredientName.isNotBlank()) {
                                quantityTotal += q
                            }
                        }
                    ) {
                        Text(
                            if (q < 1f) "${(q * 10).toInt()}/10"
                            else q.toInt().toString()
                        )
                    }
                }
            }

            // 현재 선택된 양 표시
            if (quantityTotal > 0) {
                Text(
                    text = "현재: ${if (selectedUnit in listOf("스푼", "컵")) quantityTotal.toString() else quantityTotal.toInt()}$selectedUnit",
                    fontWeight = FontWeight.Bold
                )
            }

            // 추가 버튼
            Button(
                onClick = {
                    if (ingredientName.isNotBlank() && quantityTotal > 0) {
                        val displayQuantity =
                            if (selectedUnit in listOf("스푼", "컵")) quantityTotal.toString() else quantityTotal.toInt().toString()
                        val item = "$ingredientName ${displayQuantity}${selectedUnit}"
                        addedIngredients.add(item)
                        ingredientName = ""
                        quantityTotal = 0f
                    } else {
                        Toast.makeText(context, R.string.put_ingredient_and_quantity, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add_button))
            }

            HorizontalDivider()

            // 추가된 재료 리스트
            Text(stringResource(R.string.added_ingredient))
            addedIngredients.forEach {
                Text("• $it")
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 완료 버튼
            Button(
                onClick = {
                    if (title.isNotBlank() && addedIngredients.isNotEmpty()) {
                        viewModel.updateRecipeTitle(title)
                        viewModel.updateIngredients(addedIngredients)
                        onComplete()
                    } else {
                        Toast.makeText(context, R.string.please_input_all, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.next))
            }
        }
    }
}

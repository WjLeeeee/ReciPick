package com.woojin.recipick.presentation.add_recipe.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woojin.recipick.data.datasource.local.FoodCategories
import com.woojin.recipick.presentation.theme.RecipickTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleAndRowItems(
    title: String,
    items: List<String>,
    selectedIngredients: List<String>,
    onIngredientClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp), // 좌우 패딩
            horizontalArrangement = Arrangement.spacedBy(8.dp) // 아이템 간 간격
        ) {
            items(items) { ingredient ->
                val isSelected = selectedIngredients.contains(ingredient)
                FilterChip(
                    selected = isSelected,
                    onClick = { onIngredientClick(ingredient) },
                    label = { Text(ingredient) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        selectedBorderColor = MaterialTheme.colorScheme.primary,
                        borderWidth = 1.dp,
                        selectedBorderWidth = 1.dp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleAndRowItemsPreview() {
    RecipickTheme {
        TitleAndRowItems(
            title = "제목입니다",
            items = FoodCategories.meats,
            selectedIngredients = listOf("소고기", "닭고기"),
            onIngredientClick = {}
        )
    }
}
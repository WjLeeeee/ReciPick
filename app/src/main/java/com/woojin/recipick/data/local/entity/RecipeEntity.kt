package com.woojin.recipick.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeTable")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: List<String>,
    @ColumnInfo(name = "steps")
    val steps: List<String>,
)
package com.woojin.recipick.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.woojin.recipick.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: RecipeEntity)

    @Query("DELETE FROM RecipeTable WHERE id = :recipeId")
    suspend fun delete(recipeId: Int)

    @Query("SELECT * FROM RecipeTable WHERE id = :recipeId")
    suspend fun getRecipe(recipeId: Int): RecipeEntity

    @Query("SELECT * FROM RecipeTable ORDER BY id DESC")
    fun getAll(): Flow<List<RecipeEntity>>

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)
}
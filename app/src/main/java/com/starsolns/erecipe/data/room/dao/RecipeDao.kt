package com.starsolns.erecipe.data.room.dao

import androidx.room.*
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.data.room.entities.RecipeEntity
import com.starsolns.erecipe.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favouriteEntity: FavouriteEntity)

    @Delete
    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM favourites_table")
    suspend fun deleteAllFavourites()

    @Query("SELECT * FROM favourites_table ORDER BY id ASC")
    fun getAllFavourites(): Flow<List<FavouriteEntity>>
}
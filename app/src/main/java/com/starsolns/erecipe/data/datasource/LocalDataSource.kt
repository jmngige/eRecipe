package com.starsolns.erecipe.data.datasource

import com.starsolns.erecipe.data.room.dao.RecipeDao
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.data.room.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao
) {

    suspend fun insertRecipe(recipes: RecipeEntity){
        recipeDao.insertRecipe(recipes)
    }

    fun getALlRecipes(): Flow<List<RecipeEntity>>{
        return recipeDao.getRecipes()
    }

    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity){
        recipeDao.insertFavourite(favouriteEntity)
    }

    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity){
        recipeDao.deleteFavourite(favouriteEntity)
    }

    fun getAllFavourites(): Flow<List<FavouriteEntity>>{
        return recipeDao.getAllFavourites()
    }

    fun deleteAllFavourites(){
        recipeDao.deleteAllFavourites()
    }

}
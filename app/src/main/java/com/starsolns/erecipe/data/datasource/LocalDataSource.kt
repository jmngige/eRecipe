package com.starsolns.erecipe.data.datasource

import com.starsolns.erecipe.data.room.RecipeDao
import com.starsolns.erecipe.data.room.RecipeEntity
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

}
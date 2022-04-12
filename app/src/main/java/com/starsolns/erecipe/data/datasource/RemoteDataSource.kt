package com.starsolns.erecipe.data.datasource

import com.starsolns.erecipe.data.retrofit.RecipeApi
import com.starsolns.erecipe.model.Recipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val recipeApi: RecipeApi
) {
    suspend fun getRecipes(queries: Map<String, String>): Response<Recipe>{
        return recipeApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQueries: Map<String, String>) : Response<Recipe>{
        return recipeApi.searchRecipes(searchQueries)
    }

}
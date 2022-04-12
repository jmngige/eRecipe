package com.starsolns.erecipe.data.retrofit

import com.starsolns.erecipe.model.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RecipeApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap recipes: Map<String, String>
    ): Response<Recipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQueries: Map<String, String>
    ): Response<Recipe>

}
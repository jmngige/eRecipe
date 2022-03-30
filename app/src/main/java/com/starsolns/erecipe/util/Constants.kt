package com.starsolns.erecipe.util

import retrofit2.http.QueryMap

class Constants {

    companion object {
        const val API_KEY = "e53c83412e8644538e21974c7b210bf4"
        const val BASE_URL = "https://api.spoonacular.com"


        /** API Queries filters */
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "snack"
        const val QUERY_DIET = "vegan"
        const val QUERY_FILL_INGREDIENTS = "true"
        const val QUERY_ADD_RECIPE_INFO = "true"


        /** Room variables */
        const val DATABASE_NAME = "recipes_database"
        const val TABLE_NAME = "recipes_table"

    }

}
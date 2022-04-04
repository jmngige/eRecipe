package com.starsolns.erecipe.util

import retrofit2.http.QueryMap

class Constants {

    companion object {
        const val API_KEY = "519822f169314e83b8038d2b4b4caba3"
        const val BASE_URL = "https://api.spoonacular.com"


        /** API Queries filters */
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_FILL_INGREDIENTS = "true"
        const val QUERY_ADD_RECIPE_INFO = "true"


        /** Room variables */
        const val DATABASE_NAME = "recipes_database"
        const val TABLE_NAME = "recipes_table"

    }

}
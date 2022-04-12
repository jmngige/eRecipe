package com.starsolns.erecipe.util



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

        /** Bottom Sheet Datastore variables */
        const val ERECIPE_DATASTORE_NAME = "eRecipe"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "ketogenic"
        const val PREFERENCE_MEAL_TYPE = "mealType"
        const val PREFERENCE_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCE_DIET_TYPE = "dietType"
        const val PREFERENCE_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCE_ONLINE_STATUS = "onlineStatus"


    }

}
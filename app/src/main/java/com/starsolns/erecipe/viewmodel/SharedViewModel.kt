package com.starsolns.erecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.starsolns.erecipe.util.Constants
import com.starsolns.erecipe.util.Constants.Companion.QUERY_ADD_RECIPE_INFO
import com.starsolns.erecipe.util.Constants.Companion.QUERY_API_KEY
import com.starsolns.erecipe.util.Constants.Companion.QUERY_DIET
import com.starsolns.erecipe.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.starsolns.erecipe.util.Constants.Companion.QUERY_NUMBER
import com.starsolns.erecipe.util.Constants.Companion.QUERY_TYPE

class SharedViewModel(application: Application): AndroidViewModel(application) {

    fun recipeQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = "main course"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFO] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}
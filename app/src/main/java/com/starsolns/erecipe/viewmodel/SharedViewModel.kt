package com.starsolns.erecipe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.starsolns.erecipe.data.datastore.DataStoreRepository
import com.starsolns.erecipe.util.Constants
import com.starsolns.erecipe.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.starsolns.erecipe.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.starsolns.erecipe.util.Constants.Companion.QUERY_ADD_RECIPE_INFO
import com.starsolns.erecipe.util.Constants.Companion.QUERY_API_KEY
import com.starsolns.erecipe.util.Constants.Companion.QUERY_DIET
import com.starsolns.erecipe.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.starsolns.erecipe.util.Constants.Companion.QUERY_NUMBER
import com.starsolns.erecipe.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    application: Application): AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE


    val readMealDietType = dataStoreRepository.readDatastore

    fun saveMealDietSelection(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        viewModelScope.launch {
        dataStoreRepository.saveMealandDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun recipeQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealDietType.collect{ value->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFO] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}
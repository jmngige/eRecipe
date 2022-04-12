package com.starsolns.erecipe.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
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
import com.starsolns.erecipe.util.Constants.Companion.QUERY_QUERY
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

    var networkStatus = false
    var onlineStatus = false

    val readMealDietType = dataStoreRepository.readDatastore
    val readOnlineStatus = dataStoreRepository.readOnlineStatus.asLiveData()

    fun saveMealDietSelection(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        viewModelScope.launch {
        dataStoreRepository.saveMealandDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }


    fun saveOnlineStatus(onlineStatus: Boolean){
        viewModelScope.launch {
            dataStoreRepository.saveOnlineStatus(onlineStatus)
        }
    }

    /** Query recipes from api using filters */
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

    /** Query recipes from api using a search query */
    fun searchRecipesQuery(searchQuery: String): Map<String, String>{

        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealDietType.collect{ value->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_QUERY] = searchQuery
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_ADD_RECIPE_INFO] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }

    /** display toast message on network status  */
    fun checkNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "Internet connection lost", Toast.LENGTH_LONG).show()
            saveOnlineStatus(true)
        }else if(networkStatus){
            if (onlineStatus){
                Toast.makeText(getApplication(), "Internet connection available", Toast.LENGTH_LONG).show()
                saveOnlineStatus(false)
            }
        }
    }
}
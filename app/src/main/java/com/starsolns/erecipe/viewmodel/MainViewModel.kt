package com.starsolns.erecipe.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.starsolns.erecipe.data.repository.Repository
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.data.room.entities.FoodJokeEntity
import com.starsolns.erecipe.data.room.entities.RecipeEntity
import com.starsolns.erecipe.model.FoodJoke
import com.starsolns.erecipe.model.Recipe
import com.starsolns.erecipe.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application) : AndroidViewModel(application) {

    val recipesResponse: MutableLiveData<NetworkResult<Recipe>> = MutableLiveData()
    val searchRecipeResponse : MutableLiveData<NetworkResult<Recipe>> = MutableLiveData()
    val foodJokeResponse : MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()

    val localRecipes: LiveData<List<RecipeEntity>> =  repository.local.getALlRecipes().asLiveData()
    val favouriteRecipes: LiveData<List<FavouriteEntity>> = repository.local.getAllFavourites().asLiveData()
    val foodJokes : LiveData<List<FoodJokeEntity>> = repository.local.getAllFoodJokes().asLiveData()



    /** Room Instance*/
    private fun insertRecipes(recipeEntity: RecipeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipe(recipeEntity)
        }
    }

    fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavouriteRecipe(favouriteEntity)
        }
    }
    fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavourite(favouriteEntity)
        }
    }
    fun deleteAllFavouriteRecipe(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavourites()
        }
    }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJokes(foodJokeEntity)
        }
    }


    /** Retrofit instance */
    fun getRecipes(queries: Map<String, String>){
        viewModelScope.launch {
            getRecipesSafeCall(queries)
        }
    }

    /** search recipes */
    fun searchRecipe(searchQuery: Map<String, String>){
        viewModelScope.launch {
            getSearchRecipesSafeCall(searchQuery)
        }
    }

    /** get food jokes */
    fun getFoodJoke(apiKey: String){
        viewModelScope.launch {
            getFoodJokeResponseSafeCall(apiKey)
        }
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleRecipeResponse(response)

                /** for offline caching */
                val retrievedRecipes = recipesResponse.value!!.data
                if(retrievedRecipes != null){
                    cacheRecipeToLocalDatabase(retrievedRecipes)
                }

            }catch (e: Exception){
                recipesResponse.value = NetworkResult.Error("Recipes not found", null)
            }

        }else {
            recipesResponse.value = NetworkResult.Error("No internet Connection", null)
        }
    }

    /** search recipes safe call */
    private suspend fun getSearchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchRecipeResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchRecipeResponse.value = handleRecipeResponse(response)

            }catch (e: Exception){
                recipesResponse.value = NetworkResult.Error("Recipes not found", null)
            }

        }else {
            searchRecipeResponse.value = NetworkResult.Error("No internet Connection", null)
        }
    }
    /** get food jokes safe call */
    private suspend fun getFoodJokeResponseSafeCall(apiKey: String) {
        foodJokeResponse.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try{
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val retrievedJokes = foodJokeResponse.value!!.data
                if(retrievedJokes != null){
                    cacheFoodJokes(retrievedJokes)
                }
            }catch (e: Exception){
                foodJokeResponse.value = NetworkResult.Error("Recipes not found")
            }
        }else {
            foodJokeResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("timeout please try again", null)
            }
            response.code() == 402 -> {
                return NetworkResult.Error("Api Key Limited", null)
            }
            response.isSuccessful -> {
                val jokes = response.body()
                return NetworkResult.Success(jokes!!)
            }
            else -> {
                return NetworkResult.Error(response.message(), null)
            }
        }
    }

    private fun cacheRecipeToLocalDatabase(retrievedRecipes: Recipe) {
        val recipes = RecipeEntity(retrievedRecipes)
        insertRecipes(recipes)
    }

    private fun cacheFoodJokes(retrievedJokes: FoodJoke) {
        val foodJokes = FoodJokeEntity(retrievedJokes)
        insertFoodJoke(foodJokes)
    }

    private fun handleRecipeResponse(response: Response<Recipe>): NetworkResult<Recipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("timeout please try again", null)
            }
            response.code() == 402 -> {
                return NetworkResult.Error("Api Key Limited", null)
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found", null)
            }
            response.isSuccessful -> {
                val recipes = response.body()
                return NetworkResult.Success(recipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message(), null)
            }
        }

    }


    private fun hasInternetConnection(): Boolean{

        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

 }
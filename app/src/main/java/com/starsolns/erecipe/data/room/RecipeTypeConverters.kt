package com.starsolns.erecipe.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.starsolns.erecipe.model.Recipe

class RecipeTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun recipeToString(recipe: Recipe): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
   fun fromStringToRecipe(data: String): Recipe{
       val listType = object: TypeToken<Recipe>() {}.type
        return gson.fromJson(data, listType)
    }
}
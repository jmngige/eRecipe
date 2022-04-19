package com.starsolns.erecipe.data.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.starsolns.erecipe.model.Recipe
import com.starsolns.erecipe.model.Result

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

    @TypeConverter
    fun fromResultToString(result: Result): String{
        return gson.toJson(result)
    }

    @TypeConverter
    fun fromStringToResult(data: String): Result{
        val listType = object : TypeToken<Result>() {}.type
        return gson.fromJson(data, listType)
    }
}
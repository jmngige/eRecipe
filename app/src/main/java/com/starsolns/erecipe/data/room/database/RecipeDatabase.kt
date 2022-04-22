package com.starsolns.erecipe.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.starsolns.erecipe.data.room.dao.RecipeDao
import com.starsolns.erecipe.data.room.converters.RecipeTypeConverters
import com.starsolns.erecipe.data.room.entities.FavouriteEntity
import com.starsolns.erecipe.data.room.entities.FoodJokeEntity
import com.starsolns.erecipe.data.room.entities.RecipeEntity

@Database(entities = [RecipeEntity::class, FavouriteEntity::class,  FoodJokeEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverters::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}
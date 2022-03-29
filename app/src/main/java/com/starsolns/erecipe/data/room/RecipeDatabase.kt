package com.starsolns.erecipe.data.room

import androidx.room.Database
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverters::class)
abstract class RecipeDatabase {

    abstract fun recipeDao(): RecipeDao

}
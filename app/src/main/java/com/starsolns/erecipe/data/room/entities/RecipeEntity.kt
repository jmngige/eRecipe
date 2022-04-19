package com.starsolns.erecipe.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.starsolns.erecipe.model.Recipe
import com.starsolns.erecipe.util.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RecipeEntity(
    var recipe: Recipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}
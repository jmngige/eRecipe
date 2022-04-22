package com.starsolns.erecipe.data.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.starsolns.erecipe.model.FoodJoke

@Entity(tableName = "food_jokes_table")
class FoodJokeEntity (
    @Embedded
    var foodJoke: FoodJoke
    ){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
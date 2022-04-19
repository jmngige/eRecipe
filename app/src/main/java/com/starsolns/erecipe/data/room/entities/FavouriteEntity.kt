package com.starsolns.erecipe.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.starsolns.erecipe.model.Result

@Entity(tableName = "favourites_table")
data class FavouriteEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
        )

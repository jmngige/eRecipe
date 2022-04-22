package com.starsolns.erecipe.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class FoodJoke(
    @SerializedName("text")
    var text: String
)
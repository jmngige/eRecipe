package com.starsolns.erecipe.di

import android.content.Context
import androidx.room.Room
import com.starsolns.erecipe.data.room.database.RecipeDatabase
import com.starsolns.erecipe.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDbInstance(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            DATABASE_NAME
        ).build()


    @Singleton
    @Provides
    fun provideRecipeDao(recipeDatabase: RecipeDatabase) = recipeDatabase.recipeDao()


}
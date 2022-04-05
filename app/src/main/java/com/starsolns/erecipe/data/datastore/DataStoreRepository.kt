package com.starsolns.erecipe.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.starsolns.erecipe.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.starsolns.erecipe.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.starsolns.erecipe.util.Constants.Companion.ERECIPE_DATASTORE_NAME
import com.starsolns.erecipe.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.starsolns.erecipe.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.starsolns.erecipe.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.starsolns.erecipe.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

    private val Context.datastore : DataStore<Preferences> by preferencesDataStore(
        name = ERECIPE_DATASTORE_NAME
    )

    /** store our data preference on the datastore */
    suspend fun saveMealandDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        context.datastore.edit { Pref->
            Pref[SELECTED_MEAL_TYPE] = mealType
            Pref[SELECTED_MEAL_TYPE_ID] = mealTypeId
            Pref[SELECTED_DIET_TYPE] = dietType
            Pref[SELECTED_DIET_TYPE_ID] = dietTypeId
        }
    }

    /** retrieve datastore values*/
    val readDatastore: Flow<MealDietType> = context.datastore.data
        .catch { exception->
            if(exception is IOException){
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { Pref->
            val selectedMealType = Pref[SELECTED_MEAL_TYPE] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = Pref[SELECTED_MEAL_TYPE_ID] ?: 0
            val selectedDietType = Pref[SELECTED_DIET_TYPE] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = Pref[SELECTED_DIET_TYPE_ID] ?: 0

            MealDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }


    data class MealDietType(
        var selectedMealType: String,
        var selectedMealTypeId: Int,
        var selectedDietType: String,
        var selectedDietTypeId: Int
    )


    /** variables to store in the datastore*/
    companion object {
         val SELECTED_MEAL_TYPE = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
         val SELECTED_MEAL_TYPE_ID = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val SELECTED_DIET_TYPE = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val SELECTED_DIET_TYPE_ID = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
    }

}
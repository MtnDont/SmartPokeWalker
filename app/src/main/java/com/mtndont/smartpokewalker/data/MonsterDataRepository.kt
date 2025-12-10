package com.mtndont.smartpokewalker.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "monster_data")

class MonsterDataRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val trainerName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[TRAINER_NAME] ?: "Red"
    }

    val exploreSteps: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[EXPLORE_STEPS] ?: 0
    }

    val totalWatts: Flow<Long> = context.dataStore.data.map { prefs ->
        prefs[TOTAL_WATTS] ?: 0L
    }

    suspend fun setTrainerName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[TRAINER_NAME] = name
        }
    }

    suspend fun setExploreSteps(steps: Long) {
        context.dataStore.edit { prefs ->
            prefs[EXPLORE_STEPS] = steps.toInt()
        }
    }

    suspend fun addExploreSteps(steps: Long) {
        context.dataStore.edit { prefs ->
            val total = prefs[EXPLORE_STEPS] ?: 0
            prefs[EXPLORE_STEPS] = total + steps.toInt()
        }
    }

    suspend fun addWatts(watts: Long) {
        context.dataStore.edit { prefs ->
            val total = prefs[TOTAL_WATTS] ?: 0L
            prefs[TOTAL_WATTS] = total + watts
        }
    }

    suspend fun subtractWatts(watts: Long) {
        context.dataStore.edit { prefs ->
            val total = prefs[TOTAL_WATTS] ?: 0L
            prefs[TOTAL_WATTS] = total - watts
        }
    }

    suspend fun setTotalWatts(watts: Long) {
        context.dataStore.edit { prefs ->
            prefs[TOTAL_WATTS] = watts
        }
    }

    companion object {
        private val TRAINER_NAME = stringPreferencesKey("trainer_name")
        private val EXPLORE_STEPS = intPreferencesKey("explore_steps")
        private val TOTAL_WATTS = longPreferencesKey("total_watts")
    }
}
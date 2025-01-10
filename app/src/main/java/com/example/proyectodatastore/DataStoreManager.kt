package com.example.proyectodatastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

object DataStoreManager {

    suspend fun saveNote(context: Context, noteName: String, content: String){
        val noteNameKey = stringPreferencesKey(noteName)
        context.dataStore.edit { preferences ->
            preferences[noteNameKey] = content
        }
    }

    fun getNoteName(context: Context): Flow<Map<String, String>> {
        return context.dataStore.data.map { preferences ->
            preferences.asMap().mapKeys { it.key.name }.mapValues { it.value as String }
        }
    }

    suspend fun deleteValue(context: Context, key: String) {
        val dataKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences.remove(dataKey) // Elimina la clave y su valor
        }
    }

}


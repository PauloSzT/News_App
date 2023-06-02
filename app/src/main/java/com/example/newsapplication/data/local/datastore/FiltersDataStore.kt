package com.example.newsapplication.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FiltersDataStore(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "filter_settings"
    )

    val sectionFilterFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SECTION_FILTER] ?: ""
    }

    val typeFilterFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[TYPE_FILTER] ?: ""
    }

    suspend fun setSectionFilter(filter: String) = context.dataStore.edit { mutablePreferences ->
        mutablePreferences[SECTION_FILTER] = filter
    }

    suspend fun setTypeFilter(filter: String) = context.dataStore.edit { mutablePreferences ->
        mutablePreferences[TYPE_FILTER] = filter
    }

    companion object {
        private val SECTION_FILTER = stringPreferencesKey("sectionFilter")
        private val TYPE_FILTER = stringPreferencesKey("typeFilter")
    }
}

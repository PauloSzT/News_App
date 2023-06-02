package com.example.newsapplication.data.local.repository

import androidx.datastore.preferences.core.edit
import com.example.newsapplication.data.local.datastore.FiltersDataStore
import com.example.newsapplication.data.local.models.FavoriteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocalRepository {
    fun getAllFavorites(): Flow<List<FavoriteItem>>
    fun getAllIds(): Flow<List<String>>
    suspend fun deleteAllItems()
    suspend fun insertItem(item: FavoriteItem)
    suspend fun deleteItem(item: FavoriteItem)
    fun sectionFilterFlow(): Flow<String>
    fun typeFilterFlow(): Flow<String>
    suspend fun setSectionFilter(filter: String)
    suspend fun setTypeFilter(filter: String)
}

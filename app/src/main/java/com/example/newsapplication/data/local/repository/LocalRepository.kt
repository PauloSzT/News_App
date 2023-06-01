package com.example.newsapplication.data.local.repository

import com.example.newsapplication.data.local.dto.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getAllFavorites(): Flow<List<FavoriteItem>>
    fun getAllIds(): Flow<List<String>>
    suspend fun deleteAllItems()
    suspend fun insertItem(item: FavoriteItem)
    suspend fun deleteItem(item: FavoriteItem)
}

package com.example.newsapplication.data.local

import android.content.Context
import com.example.newsapplication.data.local.dto.FavoriteItem
import kotlinx.coroutines.flow.Flow

class AppDataBaseImpl(context: Context) {
    private val favoriteDAO: FavoriteDAO = AppDatabase.getDatabase(context).itemDao()
    fun getAllFavorites(): Flow<List<FavoriteItem>> {
        return favoriteDAO.getAllFavorites()
    }

    fun getAllIds(): Flow<List<String>> {
        return favoriteDAO.getAllIds()
    }

    suspend fun deleteAllItems() {
        return favoriteDAO.deleteAllItems()
    }

    suspend fun insertItem(item: FavoriteItem) {
        return favoriteDAO.insertItem(item)
    }

    suspend fun deleteItem(item: FavoriteItem) {
        return favoriteDAO.deleteItem(item)
    }
}

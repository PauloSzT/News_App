package com.example.newsapplication.data.local

import android.content.Context
import com.example.newsapplication.data.local.models.FavoriteItem
import kotlinx.coroutines.flow.Flow

class AppDataBaseImpl(context: Context) {
    private val favoriteDAO: FavoriteDAO = AppDatabase.getDatabase(context).itemDao()
    fun getAllFavorites(): Flow<List<FavoriteItem>> {
        return favoriteDAO.getAllFavorites()
    }

    fun getAllIds(): Flow<List<String>> = favoriteDAO.getAllIds()

    fun getItemById(id: String): Flow<FavoriteItem> = favoriteDAO.getItemById(id)

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

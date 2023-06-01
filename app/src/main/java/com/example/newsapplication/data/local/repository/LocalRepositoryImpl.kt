package com.example.newsapplication.data.local.repository

import com.example.newsapplication.data.local.AppDataBaseImpl
import com.example.newsapplication.data.local.dto.FavoriteItem
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val appDataBaseImpl: AppDataBaseImpl
) : LocalRepository {

    override fun getAllFavorites(): Flow<List<FavoriteItem>> {
       return appDataBaseImpl.getAllFavorites()
    }

    override fun getAllIds(): Flow<List<String>> {
      return appDataBaseImpl.getAllIds()
    }

    override suspend fun deleteAllItems() {
        return appDataBaseImpl.deleteAllItems()
    }

    override suspend fun insertItem(item: FavoriteItem) {
        return appDataBaseImpl.insertItem(item)
    }

    override suspend fun deleteItem(item: FavoriteItem) {
        return appDataBaseImpl.deleteItem(item)
    }

}

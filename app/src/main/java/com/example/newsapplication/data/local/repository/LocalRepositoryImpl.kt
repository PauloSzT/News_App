package com.example.newsapplication.data.local.repository

import com.example.newsapplication.data.local.AppDataBaseImpl
import com.example.newsapplication.data.local.datastore.FiltersDataStore
import com.example.newsapplication.data.local.models.FavoriteItem
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(
    private val appDataBaseImpl: AppDataBaseImpl,
    private val filtersDataStore: FiltersDataStore
) : LocalRepository {
    override fun sectionFilterFlow(): Flow<String> = filtersDataStore.sectionFilterFlow

    override fun typeFilterFlow(): Flow<String> = filtersDataStore.typeFilterFlow

    override suspend fun setSectionFilter(filter: String) {
        filtersDataStore.setSectionFilter(filter)
    }

    override suspend fun setTypeFilter(filter: String) {
        filtersDataStore.setTypeFilter(filter)
    }

    override fun getAllFavorites(): Flow<List<FavoriteItem>> = appDataBaseImpl.getAllFavorites()

    override fun getAllIds(): Flow<List<String>> = appDataBaseImpl.getAllIds()

    override fun getItemById(id: String): Flow<FavoriteItem> = appDataBaseImpl.getItemById(id)

    override suspend fun deleteAllItems() = appDataBaseImpl.deleteAllItems()

    override suspend fun insertItem(item: FavoriteItem) = appDataBaseImpl.insertItem(item)

    override suspend fun deleteItem(item: FavoriteItem) = appDataBaseImpl.deleteItem(item)
}

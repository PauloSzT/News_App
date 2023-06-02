package com.example.newsapplication.data.remote.repository

import androidx.paging.PagingData
import com.example.newsapplication.data.remote.dto.SearchResult
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
   suspend fun getSearchByQuery(query: String, page: Int): SearchResult
   fun getPaginatedNews(query: String, idsList: List<String>): Flow<PagingData<UiNews>>
}

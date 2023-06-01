package com.example.newsapplication.data.remote.repository

import com.example.newsapplication.data.remote.dto.SearchResult

interface RemoteRepository {
   suspend fun getSearchByQuery(query: String): SearchResult
}

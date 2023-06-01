package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.dto.SearchResult

interface PostService {
    suspend fun getSearchByQuery(query: String): SearchResult
}
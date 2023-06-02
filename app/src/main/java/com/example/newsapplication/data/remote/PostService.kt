package com.example.newsapplication.data.remote

import com.example.newsapplication.data.remote.models.SearchResult

interface PostService {
    suspend fun getSearchByQuery(
        query: String,
        page: Int,
        sectionFilter: String,
        typeFilter: String
    ): SearchResult
}

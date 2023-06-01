package com.example.newsapplication.data.remote.repository

import com.example.newsapplication.data.remote.PostService
import com.example.newsapplication.data.remote.dto.SearchResult

class RemoteRepositoryImpl(private val postService: PostService): RemoteRepository {
    override suspend fun getSearchByQuery(query: String): SearchResult {
        return postService.getSearchByQuery(query)
    }
}

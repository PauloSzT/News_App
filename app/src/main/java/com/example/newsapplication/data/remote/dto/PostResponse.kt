package com.example.newsapplication.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val response: PostResponse
)

@Serializable
data class PostResponse(
    val status: String,
    val userTier: String,
    val total: Int,
    val startIndex: Int,
    val pageSize: Int,
    val currentPage: Int,
    val pages: Int,
    val orderBy: String,
    val result: List<News>
)

@Serializable
data class News(

    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String
)

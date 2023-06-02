package com.example.newsapplication.data.remote.models

import com.example.newsapplication.ui.models.UiNews
import com.example.newsapplication.ui.utils.ToUiMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("response")
    val response: PostResponse
)

@Serializable
data class PostResponse(
    val status: String?,
    val userTier: String?,
    val total: Int?,
    val startIndex: Int?,
    val pageSize: Int?,
    val currentPage: Int?,
    val pages: Int?,
    val orderBy: String?,
    val results: List<News>?
)

@Serializable
data class News(

    val id: String?,
    val type: String?,
    val sectionId: String?,
    val sectionName: String?,
    val webPublicationDate: String?,
    val webTitle: String?,
    val webUrl: String?,
    val apiUrl: String?,
    val isHosted: Boolean?,
    val pillarId: String?,
    val pillarName: String?
) : ToUiMapper<UiNews> {
    override fun mapToUiNews(ids: List<String>): UiNews {
        return UiNews(
            id = id ?: "Undefined",
            type = type ?: "Unknown",
            sectionName = sectionName ?: "Unknown",
            webPublicationDate = webPublicationDate ?: "Undefined",
            webTitle = webTitle ?: "Unknown",
            webUrl = webUrl ?: "Unknown",
            isFavorite = ids.contains(id)
        )
    }
}

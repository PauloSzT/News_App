package com.example.newsapplication.data.remote.models

import com.example.newsapplication.core.CoreConstants.UNDEFINED
import com.example.newsapplication.core.CoreConstants.UNKNOWN
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
    override fun mapToUiModel(): UiNews {
        return UiNews(
            id = id ?: UNDEFINED,
            type = type ?: UNKNOWN,
            sectionName = sectionName ?: UNKNOWN,
            webPublicationDate = webPublicationDate ?: UNDEFINED,
            webTitle = webTitle ?: UNKNOWN,
            webUrl = webUrl ?: UNKNOWN
        )
    }
}

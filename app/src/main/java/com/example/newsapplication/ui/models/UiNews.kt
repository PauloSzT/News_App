package com.example.newsapplication.ui.models

import com.example.newsapplication.data.local.dto.FavoriteItem
import com.example.newsapplication.ui.utils.ToDataBaseMapper
import com.example.newsapplication.ui.utils.ToUiModelIntegrationMapper

data class UiNews(
    val id: String,
    val type: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val isFavorite: Boolean
) : ToDataBaseMapper<FavoriteItem>, ToUiModelIntegrationMapper<UiDetailNews> {
    override fun mapToDataBaseModel(): FavoriteItem {
        return FavoriteItem(
            itemId = id,
            title = webTitle,
            date = webPublicationDate,
            type = type,
            sectionName = sectionName,
            webUrl = webUrl
        )
    }

    override fun mapToUiModelIntegration(): UiDetailNews {
        return UiDetailNews(
            type = type,
            sectionName = sectionName,
            webPublicationDate = webPublicationDate,
            webTitle = webTitle,
            webUrl = webUrl
        )
    }
}

package com.example.newsapplication.ui.models

import com.example.newsapplication.data.local.models.FavoriteItem
import com.example.newsapplication.ui.utils.ToDataBaseMapper

data class UiNews(
    val id: String,
    val type: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String
) : ToDataBaseMapper<FavoriteItem>{
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
}

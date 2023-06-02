package com.example.newsapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapplication.ui.models.UiDetailNews
import com.example.newsapplication.ui.utils.ToUiModelIntegrationMapper

@Entity(tableName = "favorite")
data class FavoriteItem(

    @PrimaryKey
    val itemId: String,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "date")
    val date: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "webUrl")
    val webUrl: String?,

    @ColumnInfo(name = "sectionName")
    val sectionName: String?

): ToUiModelIntegrationMapper<UiDetailNews>{
    override fun mapToUiModelIntegration(): UiDetailNews {
        return UiDetailNews(
            type = type ?: "Unknown",
            sectionName = sectionName ?: "Unknown",
            webPublicationDate = date ?: "Undefined",
            webTitle = title ?: "Unknown",
            webUrl = webUrl ?: "Unknown"
        )
    }
}

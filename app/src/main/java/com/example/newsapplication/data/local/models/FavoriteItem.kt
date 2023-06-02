package com.example.newsapplication.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapplication.core.CoreConstants.UNDEFINED
import com.example.newsapplication.core.CoreConstants.UNKNOWN
import com.example.newsapplication.ui.models.UiNews
import com.example.newsapplication.ui.utils.ToUiMapper

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

): ToUiMapper<UiNews>{
    override fun mapToUiModel(): UiNews {
        return UiNews(
            id = itemId,
            type = type ?: UNKNOWN,
            sectionName = sectionName ?: UNKNOWN,
            webPublicationDate = date ?: UNDEFINED,
            webTitle = title ?: UNKNOWN,
            webUrl = webUrl ?: UNKNOWN,
        )
    }
}

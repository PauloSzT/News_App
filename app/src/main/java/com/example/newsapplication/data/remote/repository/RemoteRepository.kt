package com.example.newsapplication.data.remote.repository

import androidx.paging.PagingData
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getPaginatedNews(
        query: String,
        sectionFilter: String,
        typeFilter: String
    ): Flow<PagingData<UiNews>>
}

package com.example.newsapplication.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapplication.data.remote.PostService
import com.example.newsapplication.data.remote.paging.NewsPagingSource
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.Flow

class RemoteRepositoryImpl(private val postService: PostService) : RemoteRepository {

    override fun getPaginatedNews(
        query: String,
        sectionFilter: String,
        typeFilter: String
    ): Flow<PagingData<UiNews>> = Pager(
        initialKey = null,
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = {
            NewsPagingSource(
                postService,
                query,
                sectionFilter,
                typeFilter
            )
        }
    ).flow
}

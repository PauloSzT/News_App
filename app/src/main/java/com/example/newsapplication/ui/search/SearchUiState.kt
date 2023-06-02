package com.example.newsapplication.ui.search

import androidx.paging.PagingData
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

data class SearchUiState(
    val searchResultList: StateFlow<MutableList<UiNews>>,
    val isLoading: StateFlow<Boolean>,
    val searchValue: StateFlow<String>,
    val onQueryChange: (String) -> Unit,
    val newsPaginatedItemsProvider: StateFlow<Flow<PagingData<UiNews>>?>
)

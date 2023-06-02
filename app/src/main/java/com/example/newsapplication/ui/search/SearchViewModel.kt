package com.example.newsapplication.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.local.repository.LocalRepository
import com.example.newsapplication.data.remote.repository.RemoteRepository
import com.example.newsapplication.ui.models.UiDetailNews
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val remoteRepository: RemoteRepository,
    localRepository: LocalRepository
) : ViewModel() {

    private val searchResultList = MutableStateFlow<MutableList<UiNews>>(mutableListOf())
    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow("")
    private val currentItemDetail = MutableStateFlow<UiDetailNews?>(null)
    private val idsList = localRepository.getAllIds()
    private val newsPaginatedItemsProvider = combine(searchValue, idsList) { query, list ->
        if (query.isNotEmpty()) {
            remoteRepository.getPaginatedNews(query, idsList = list)
        } else {
            null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val searchUiState = SearchUiState(
        searchResultList = searchResultList,
        searchValue = searchValue,
        isLoading = isLoading,
        onQueryChange = ::onQueryChange,
        newsPaginatedItemsProvider = newsPaginatedItemsProvider,
    )

    private fun onQueryChange(query: String) {
        searchValue.value = query
//        isLoading.value = true
    }
}

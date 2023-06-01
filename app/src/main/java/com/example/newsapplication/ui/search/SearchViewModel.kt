package com.example.newsapplication.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.local.repository.LocalRepository
import com.example.newsapplication.data.remote.repository.RemoteRepository
import com.example.newsapplication.ui.models.UiDetailNews
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private var searchResultList = MutableStateFlow<MutableList<UiNews>>(mutableListOf())
    private var isLoading = MutableStateFlow(false)
    private var searchValue = MutableStateFlow("")
    private var currentItemDetail = MutableStateFlow<UiDetailNews?>(null)

    val searchUiState = SearchUiState(
        searchResultList = searchResultList,
        searchValue = searchValue,
        isLoading = isLoading,
        onQueryChange = ::onQueryChange

    )

    private fun onQueryChange(query: String) {
        searchValue.value = query
        isLoading.value = true
        viewModelScope.launch{
            val idsList = localRepository.getAllIds().first()
            searchResultList.value =
                remoteRepository.getSearchByQuery(query).response.results.map { item->
                    item.mapToUiNews(idsList)
                }.toMutableList()
            isLoading.value = false
        }
    }
}

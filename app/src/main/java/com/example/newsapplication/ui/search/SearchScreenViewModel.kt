package com.example.newsapplication.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.core.CoreConstants.EMPTY_STRING
import com.example.newsapplication.core.CoreConstants.SECTION_FILTERS
import com.example.newsapplication.core.CoreConstants.TYPE_FILTERS
import com.example.newsapplication.data.local.repository.LocalRepository
import com.example.newsapplication.data.remote.repository.RemoteRepository
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow(EMPTY_STRING)
    private val favoritesIds = localRepository.getAllIds()
    private val favoritesIdsState = favoritesIds.stateIn(
        viewModelScope, SharingStarted.Lazily,
        emptyList()
    )
    private val sectionFilterFlow = localRepository.sectionFilterFlow()
    private val typeFilterFlow = localRepository.typeFilterFlow()
    private val sectionFilterState =
        sectionFilterFlow.stateIn(viewModelScope, SharingStarted.Lazily, EMPTY_STRING)
    private val typeFilterState =
        typeFilterFlow.stateIn(viewModelScope, SharingStarted.Lazily, EMPTY_STRING)
    private val sectionFilterList = MutableStateFlow(SECTION_FILTERS)
    private val typeFilterList = MutableStateFlow(TYPE_FILTERS)

    private val newsPaginatedItemsProvider = combine(
        searchValue,
        sectionFilterFlow,
        typeFilterFlow
    ) { query, sectionFilter, typeFilter ->
        if (query.isNotEmpty()) {
            isLoading.value = false
            remoteRepository.getPaginatedNews(
                query = query,
                sectionFilter = sectionFilter,
                typeFilter = typeFilter
            )
        } else {
            null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)


    val searchUiState = SearchUiState(
        searchValue = searchValue,
        isLoading = isLoading,
        newsPaginatedItemsProvider = newsPaginatedItemsProvider,
        sectionFilterList = sectionFilterList,
        typeFilterList = typeFilterList,
        typeFilterState = typeFilterState,
        sectionFilterState = sectionFilterState,
        favoritesIdsState = favoritesIdsState,
        onSectionFilterClick = ::onSectionFilterClick,
        onTypeFilterClick = ::onTypeFilterClick,
        onQueryChange = ::onQueryChange,
        onFavoriteClick = ::onFavoriteClick
    )

    private fun onQueryChange(query: String) {
        isLoading.value = true
        searchValue.value = if (query.length == 1) query.trim() else query
        isLoading.value = false
    }

    private fun onSectionFilterClick(filter: String) {
        isLoading.value = true
        viewModelScope.launch { localRepository.setSectionFilter(filter) }
    }

    private fun onTypeFilterClick(filter: String) {
        isLoading.value = true
        viewModelScope.launch { localRepository.setTypeFilter(filter) }
    }

    private fun onFavoriteClick(item: UiNews) {
        viewModelScope.launch {
            if (favoritesIdsState.value.contains(item.id)) {
                localRepository.deleteItem(item.mapToDataBaseModel())
            } else {
                localRepository.insertItem(item.mapToDataBaseModel())
            }
        }
    }
}

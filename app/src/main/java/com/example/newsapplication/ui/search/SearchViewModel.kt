package com.example.newsapplication.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.core.CoreConstants.SECTION_FILTERS
import com.example.newsapplication.core.CoreConstants.TYPE_FILTERS
import com.example.newsapplication.data.local.repository.LocalRepository
import com.example.newsapplication.data.remote.repository.RemoteRepository
import com.example.newsapplication.ui.models.UiDetailNews
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

    private val searchResultList = MutableStateFlow<MutableList<UiNews>>(mutableListOf())
    private val isLoading = MutableStateFlow(false)
    private val searchValue = MutableStateFlow("")
    private val currentItemDetail = MutableStateFlow<UiDetailNews?>(null)
    private val idsList = localRepository.getAllIds()
    private val sectionFilterFlow = localRepository.sectionFilterFlow()
    private val typeFilterFlow = localRepository.typeFilterFlow()
    private val sectionFilterState =
        sectionFilterFlow.stateIn(viewModelScope, SharingStarted.Lazily, "")
    private val typeFilterState =
        typeFilterFlow.stateIn(viewModelScope, SharingStarted.Lazily, "")
    private val sectionFilterList = MutableStateFlow(SECTION_FILTERS)
    private val typeFilterList = MutableStateFlow(TYPE_FILTERS)

    private val newsPaginatedItemsProvider = combine(
        searchValue,
        sectionFilterFlow,
        typeFilterFlow
    ) { query, sectionFilter, typeFilter ->
        if (query.isNotEmpty()) {
            val list = idsList.first()
            isLoading.value = false
            remoteRepository.getPaginatedNews(
                query = query,
                idsList = list,
                sectionFilter = sectionFilter,
                typeFilter = typeFilter
            )
        } else {
            null
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)


    val searchUiState = SearchUiState(
        searchResultList = searchResultList,
        searchValue = searchValue,
        isLoading = isLoading,
        newsPaginatedItemsProvider = newsPaginatedItemsProvider,
        sectionFilterList = sectionFilterList,
        typeFilterList = typeFilterList,
        typeFilterState = typeFilterState,
        sectionFilterState = sectionFilterState,
        onSectionFilterClick = ::onSectionFilterClick,
        onTypeFilterClick = ::onTypeFilterClick,
        onQueryChange = ::onQueryChange
    )

    private fun onQueryChange(query: String) {
        isLoading.value = true
        searchValue.value = if (query.length == 1) query.trim() else query
    }

    private fun onSectionFilterClick(filter: String) {
        isLoading.value = true
        viewModelScope.launch { localRepository.setSectionFilter(filter) }
    }

    private fun onTypeFilterClick(filter: String) {
        isLoading.value = true
        viewModelScope.launch { localRepository.setTypeFilter(filter) }
    }
}

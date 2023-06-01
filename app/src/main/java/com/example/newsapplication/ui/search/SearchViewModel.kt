package com.example.newsapplication.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.remote.repository.RemoteRepository
import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val remoteRepository: RemoteRepository
) : ViewModel() {
    private var searchResultList = MutableStateFlow<MutableList<UiNews>>(mutableListOf())
    private var isLoading = MutableStateFlow(false)
    private var searchValue = MutableStateFlow("")

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
            searchResultList.value =
                remoteRepository.getSearchByQuery(query).response.results.map { item->
                    item.mapToUiNews()
                }.toMutableList()
            isLoading.value = false
        }
        Log.wtf("paulocode", "${searchResultList}")
    }
}

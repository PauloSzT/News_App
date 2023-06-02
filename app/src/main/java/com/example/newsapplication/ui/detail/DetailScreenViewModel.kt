package com.example.newsapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.local.repository.LocalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailScreenViewModel(
    id: String,
    localRepository: LocalRepository
) : ViewModel() {
    private val detailItem = localRepository.getItemById(id).map { item ->
        item.mapToUiModel()
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
    val detailUiState = DetailUiState(
        detailItem = detailItem
    )
}

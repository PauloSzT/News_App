package com.example.newsapplication.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.ui.favorites.FavoriteUiState
import com.example.newsapplication.data.local.repository.LocalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FavoritesScreenViewModel(
    localRepository: LocalRepository
) : ViewModel() {
    private val favoriteList = localRepository.getAllFavorites().map { list ->
        list.map { item ->
            item.mapToUiModel()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteUiState = FavoriteUiState(
        favoriteList = favoriteList,
    )
}

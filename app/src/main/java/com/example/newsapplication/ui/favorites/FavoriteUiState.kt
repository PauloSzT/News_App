package com.example.movieapp.ui.favorites

import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.StateFlow

data class FavoriteUiState(
    val favoriteList: StateFlow<List<UiNews>>
)

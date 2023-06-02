package com.example.newsapplication.ui.detail

import com.example.newsapplication.ui.models.UiNews
import kotlinx.coroutines.flow.StateFlow

class DetailUiState(
    val detailItem: StateFlow<UiNews?>
)

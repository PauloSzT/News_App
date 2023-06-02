package com.example.newsapplication.ui.detail

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newsapplication.core.CoreConstants.EMPTY_STRING

@Composable
fun DetailScreen(
    viewModel: DetailScreenViewModel
) {
    DetailScreenContent(viewModel.detailUiState)
}

@Composable
fun DetailScreenContent(
    detailUiState: DetailUiState
) {
    val detailItem by detailUiState.detailItem.collectAsState()
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(detailItem?.webUrl ?: EMPTY_STRING)
            }
        },
        update = {
            it.loadUrl(detailItem?.webUrl ?: EMPTY_STRING)
        }
    )
}

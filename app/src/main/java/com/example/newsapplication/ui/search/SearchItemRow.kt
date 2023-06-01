package com.example.newsapplication.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapplication.ui.models.UiNews

@Composable
fun SearchItemRow(
    uiNews: UiNews
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row() {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(2.5f),

                ) {
                Text(
                    text = "${uiNews.webTitle} (${uiNews.type})",
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    softWrap = true
                )
                Text(
                    text = uiNews.webPublicationDate
                )
            }
        }
    }
}
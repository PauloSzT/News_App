package com.example.newsapplication.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.newsapplication.R
import com.example.newsapplication.ui.models.UiNews

@Composable
fun SearchItemRow(
    uiNews: UiNews,
    favoritesIdsState: List<String>,
    onFavoriteClick: (UiNews) -> Unit,
    navigateToDetails: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToDetails(uiNews.id)},
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
                    text = stringResource(id = R.string.publication_date,uiNews.webPublicationDate)
                )
            }
            Icon(
                modifier = Modifier
                    .padding(top = 16.dp, end = 8.dp)
                    .weight(0.5f)
                    .clickable { onFavoriteClick(uiNews) },
                painter = painterResource(
                    if (favoritesIdsState.contains(uiNews.id)) R.drawable.ic_start_fill else R.drawable.ic_start_empty
                ),
                contentDescription = null
            )
        }
    }
}

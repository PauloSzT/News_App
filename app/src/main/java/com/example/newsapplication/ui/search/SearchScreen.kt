package com.example.newsapplication.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.paging.compose.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapplication.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
) {
    SearchScreenContent(viewModel.searchUiState)
}

@Composable
fun SearchScreenContent(
    searchUiState: SearchUiState
) {
    val searchResultList by searchUiState.searchResultList.collectAsState()
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchValue.collectAsState()
    val newsPaginatedItemsProvider by searchUiState.newsPaginatedItemsProvider.collectAsState()
    val newsPaginatedItems = newsPaginatedItemsProvider?.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.filters))
            Button(
                onClick = {
//                    searchUiState.onFilterClick(FilterType.Movies)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
//                    if (filterTypeList.contains(FilterType.Movies)) {
                    MaterialTheme.colorScheme.primary
//                    } else {
//                        MaterialTheme.colorScheme.secondary
//                    }
                ),
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) {
                Text(text = "")
            }
            Button(
                onClick = {
//                    searchUiState.onFilterClick(FilterType.Series)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
//                    if (filterTypeList.contains(FilterType.Series)) {
                    MaterialTheme.colorScheme.primary
//                    } else {
//                        MaterialTheme.colorScheme.secondary
//                    }
                ),
                elevation = ButtonDefaults.buttonElevation(1.dp)
            ) {
                Text(text = "")
            }
        }
        Row {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = searchValue,
                onValueChange = { value ->
                    searchUiState.onQueryChange(value)
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                LoadingScreen()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    newsPaginatedItems?.let {
                        items(newsPaginatedItems) {
                            it?.let { SearchItemRow(uiNews = it) }

                        }
                    }
//                    if (searchResultList.isEmpty()) {
//                        item { NoResult() }
//                    } else {
//                        searchResultList.forEach { searchResult ->
//                            item {
//                                SearchItemRow(
//                                    searchResult
//                                )
//                            }
//                            item {
//                                Spacer(modifier = Modifier.height(8.dp))
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun NoResult() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.no_search_results))
    }
}

@Preview
@Composable
fun SearchScreenContentPreview() {

}
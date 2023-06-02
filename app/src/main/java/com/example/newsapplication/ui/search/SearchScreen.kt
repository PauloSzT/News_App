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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.movieapp.ui.theme.NewsAppTheme
import com.example.newsapplication.R
import com.example.newsapplication.ui.components.FiltersDrawer
import com.example.newsapplication.ui.favorites.FavoriteConstants
import com.example.newsapplication.ui.navigation.NavItem
import com.example.newsapplication.ui.search.SearchScreenConstants.OPEN_FILTERS
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = koinViewModel(),
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                FiltersDrawer(
                    searchUiState = viewModel.searchUiState,
                    closeDrawer = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        gesturesEnabled = true
    ) {
        SearchScreenContent(
            searchUiState = viewModel.searchUiState,
            openFilterDrawer = { scope.launch { drawerState.open() } }) { item ->
            navHostController.navigate(NavItem.Detail.routeWithArgs(item))
        }
    }
}

@Composable
fun SearchScreenContent(
    searchUiState: SearchUiState,
    openFilterDrawer: () -> Unit,
    navigateToDetails: (String) -> Unit
) {
    val isLoading by searchUiState.isLoading.collectAsState()
    val searchValue by searchUiState.searchValue.collectAsState()
    val newsPaginatedItemsProvider by searchUiState.newsPaginatedItemsProvider.collectAsState()
    val newsPaginatedItems = newsPaginatedItemsProvider?.collectAsLazyPagingItems()
    val favoritesIdsState by searchUiState.favoritesIdsState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { openFilterDrawer() },
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    MaterialTheme.colorScheme.onBackground
                ),
                elevation = ButtonDefaults.buttonElevation(1.dp),
                shape = ShapeDefaults.Small
            ) {
                Text(text = OPEN_FILTERS)
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
                        items(
                            count = newsPaginatedItems.itemCount,
                            key = newsPaginatedItems.itemKey(),
                            contentType = newsPaginatedItems.itemContentType()
                        ) { index ->
                            val item = newsPaginatedItems[index]
                            item?.let { uiNews ->
                                SearchItemRow(
                                    uiNews = uiNews,
                                    favoritesIdsState,
                                    searchUiState.onFavoriteClick,
                                    navigateToDetails = {
                                        val id = URLEncoder.encode(
                                            uiNews.id,
                                            FavoriteConstants.UTF
                                        )
                                        navigateToDetails(id)
                                    }
                                )
                            } ?: NoResult()
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                    } ?: item { NoResult() }
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

@Preview(showBackground = true)
@Composable
fun SearchScreenContentPreview() {
    val searchUiState = SearchUiState(

        isLoading = MutableStateFlow(false),
        searchValue = MutableStateFlow(""),
        sectionFilterList = MutableStateFlow(emptyList()),
        typeFilterList = MutableStateFlow(emptyList()),
        typeFilterState = MutableStateFlow(""),
        sectionFilterState = MutableStateFlow(""),
        favoritesIdsState = MutableStateFlow(emptyList()),
        onSectionFilterClick = { },
        onTypeFilterClick = { },
        newsPaginatedItemsProvider = MutableStateFlow(null),
        onQueryChange = {},
        onFavoriteClick = {}
    )
    NewsAppTheme {
        SearchScreenContent(searchUiState = searchUiState, {}, {})
    }
}


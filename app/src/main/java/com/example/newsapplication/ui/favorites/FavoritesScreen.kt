package com.example.newsapplication.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieapp.ui.favorites.FavoriteUiState
import com.example.movieapp.ui.theme.NewsAppTheme
import com.example.newsapplication.R
import com.example.newsapplication.ui.favorites.FavoriteConstants.UTF
import com.example.newsapplication.ui.models.UiNews
import com.example.newsapplication.ui.navigation.NavItem
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder
import kotlin.math.abs

@Composable
fun FavoritesScreen(
    viewModel: FavoritesScreenViewModel = koinViewModel(),
    navHostController: NavHostController
) {
    FavoritesScreenContent(viewModel.favoriteUiState) { id ->
        navHostController.navigate(NavItem.Detail.routeWithArgs(id))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreenContent(
    favoriteUiState: FavoriteUiState,
    navigateToDetails: (String) -> Unit
) {
    val favoriteList by favoriteUiState.favoriteList.collectAsState()
    val favoriteListState = rememberPagerState { favoriteList.size }
    val windowWidth = LocalConfiguration.current.screenWidthDp.dp
    val pageSize = windowWidth.times(0.5f)
    val padding = windowWidth.times(0.2f)

    if (favoriteList.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = favoriteListState,
                pageSize =
                PageSize.Fixed(
                    pageSize + padding
                ),
                contentPadding = PaddingValues(
                    horizontal = (windowWidth - pageSize ).div(
                        2
                    )
                )
            ) { pageNumber ->
                FavoriteItemComponent(
                    modifier = Modifier.width(pageSize).graphicsLayer {
                        val currentOffset =
                            abs(favoriteListState.calculateCurrentOffsetForPage(pageNumber))
                        scaleX = 1.5f - 0.5.times(currentOffset).toFloat()
                        scaleY = 1.5f - 0.5.times(currentOffset).toFloat()

                    },
                    uiNews = favoriteList[pageNumber],
                    onCardClick = {
                        val id = URLEncoder.encode(favoriteList[pageNumber].id, UTF)
                        navigateToDetails(id)
                    }
                )
            }
        }

    } else {
        NoFavoritesSaved()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
fun FavoriteItemComponent(
    uiNews: UiNews,
    modifier: Modifier,
    onCardClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onCardClick(uiNews.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = uiNews.webTitle,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(id = R.string.type, uiNews.type),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(
                    id = R.string.publication_date,
                    uiNews.webPublicationDate
                ),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stringResource(id = R.string.section, uiNews.sectionName),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}


@Composable
fun NoFavoritesSaved() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.no_favorites_saved))
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    val favoriteUiState = FavoriteUiState(
        favoriteList = MutableStateFlow(
            listOf(
                UiNews(
                    id = "football/blog/2023/jan/30/inaki-williams-la-liga-athletic-bilbao",
                    type = "article",
                    sectionName = "football",
                    webPublicationDate = "2023-01-30T16:50:28Z",
                    webTitle = "Iñaki Williams’ 251-game run is over. But some things stay the same",
                    webUrl = "https://www.theguardian.com/football/blog/2023/jan/30/inaki-williams-la-liga-athletic-bilbao"
                )
            )
        )
    )
    NewsAppTheme {
        FavoritesScreenContent(favoriteUiState = favoriteUiState) {}
    }
}

package com.example.newsapplication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.newsapplication.ui.search.SearchUiState

@Composable
fun FiltersDrawer(
    searchUiState: SearchUiState,
    closeDrawer: () -> Unit
) {
    val sectionFilterList by searchUiState.sectionFilterList.collectAsState()
    val typeFilterList by searchUiState.typeFilterList.collectAsState()
    val sectionFilterState by searchUiState.sectionFilterState.collectAsState()
    val typeFilterState by searchUiState.typeFilterState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Section Filters",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            sectionFilterList.forEach { filter ->
                item {
                    Button(
                        onClick = { searchUiState.onSectionFilterClick(
                            if(sectionFilterState == filter) "" else filter
                        ) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                    if (sectionFilterState == filter) {
                            MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    }
                        )
                    ) {
                        Text(text = filter)
                    }
                }
            }
        }
        Text(
            text = "Type Filters",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            typeFilterList.forEach { filter ->
                item {
                    Button(
                        onClick = { searchUiState.onTypeFilterClick(
                            if(typeFilterState == filter) "" else filter
                        ) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                            if (typeFilterState == filter) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            }
                        )
                    ) {
                        Text(text = filter)
                    }
                }
            }
        }
        NavigationDrawerItem(
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Close",
                    textAlign = TextAlign.Center
                )
            },
            selected = false,
            onClick = { closeDrawer() },
            modifier = Modifier.width(200.dp),
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = MaterialTheme.colorScheme.onBackground,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedBadgeColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

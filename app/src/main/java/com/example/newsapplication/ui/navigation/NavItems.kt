package com.example.newsapplication.ui.navigation

import com.example.newsapplication.R
import com.example.newsapplication.ui.navigation.NavConstants.DETAIL_SCREEN_ROUTE
import com.example.newsapplication.ui.navigation.NavConstants.DETAIL_SCREEN_TITLE
import com.example.newsapplication.ui.navigation.NavConstants.FAVORITES_SCREEN_ROUTE
import com.example.newsapplication.ui.navigation.NavConstants.FAVORITES_SCREEN_TITLE
import com.example.newsapplication.ui.navigation.NavConstants.SEARCH_SCREEN_ROUTE
import com.example.newsapplication.ui.navigation.NavConstants.SEARCH_SCREEN_TITLE

sealed class NavItem(var title: String, var icon: Int, var route: String) {

    object Detail : NavItem(DETAIL_SCREEN_TITLE, R.drawable.ic_detail, DETAIL_SCREEN_ROUTE)
    object Search : NavItem(SEARCH_SCREEN_TITLE, R.drawable.ic_search, SEARCH_SCREEN_ROUTE)
    object Favorites : NavItem(FAVORITES_SCREEN_TITLE, R.drawable.ic_favorites, FAVORITES_SCREEN_ROUTE)

    companion object {
        fun String.title(): String {
            return when (this) {
                Detail.route -> Detail.title
                Search.route -> Search.title
                Favorites.route -> Favorites.title
                else -> ""
            }
        }
    }
}

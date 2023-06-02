package com.example.newsapplication.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapplication.core.CoreConstants.EMPTY_STRING
import com.example.newsapplication.ui.detail.DetailScreen
import com.example.newsapplication.ui.favorites.FavoritesScreen
import com.example.newsapplication.ui.navigation.NavConstants.DETAIL_ARGUMENT_KEY
import com.example.newsapplication.ui.search.SearchScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = NavItem.Search.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(
            route = NavItem.Detail.route,
            arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY) ?: EMPTY_STRING
            DetailScreen(getViewModel(parameters = { parametersOf(id) }))
        }
        composable(route = NavItem.Favorites.route) {
            FavoritesScreen(navHostController = navHostController)
        }
        composable(route = NavItem.Search.route) {
            SearchScreen(navHostController = navHostController)
        }
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    val items = listOf(NavItem.Favorites, NavItem.Search)
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                modifier = Modifier
                    .height(50.dp)
                    .align(alignment = Alignment.CenterVertically),
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}
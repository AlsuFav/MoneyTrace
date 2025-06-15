package ru.fav.moneytrace

import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.base.theme.Providers
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.navigation.ui.AppNavGraph
import ru.fav.moneytrace.navigation.ui.BottomNavigationBar
import ru.fav.moneytrace.navigation.ui.rememberBottomNavItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigationManager: NavigationManager) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    val bottomNavItems = rememberBottomNavItems(navController, navigationManager)

    var topAppBarState by remember { mutableStateOf<TopAppBarState?>(null) }

    Scaffold(
        topBar = {
            topAppBarState?.let { state ->
                if (state.isVisible) {
                    CenterAlignedTopAppBar(
                        title = {
                            state.title?.let {
                                Text(
                                    text = it,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = state.backgroundColor ?: Providers.color.primary
                        ),
                        navigationIcon = { state.navigationIcon?.invoke() },
                        actions = state.actions
                    )
                }
            }
        },
        bottomBar = {
           BottomNavigationBar(
               bottomNavItems = bottomNavItems,
               currentRoute = currentRoute
           )
        }
    ) { innerPadding ->
        AppNavGraph(
            navigationManager = navigationManager,
            navController = navController,
            onTopAppBarStateChange = { state ->
                topAppBarState = state
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
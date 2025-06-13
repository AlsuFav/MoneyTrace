package ru.fav.moneytrace.basefeature

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.navigation.NavGraph
import ru.fav.moneytrace.navigation.NavigationManager

interface FeatureBase {
    val navGraph: NavGraph

    val name: String

    fun routes(builder: NavGraphBuilder,
               navController: NavController,
               navigationManager: NavigationManager,
               topAppBarSetter: (TopAppBarState) -> Unit
    )

    fun navigate(navController: NavController, args: Bundle? = null)
}
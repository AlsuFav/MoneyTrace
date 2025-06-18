package ru.fav.moneytrace.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface FeatureNav {
    val navGraph: NavGraph

    val name: String

    fun routes(builder: NavGraphBuilder,
               navController: NavController,
               navigationManager: NavigationManager,
    )

    fun navigate(navController: NavController, args: Bundle? = null)
}
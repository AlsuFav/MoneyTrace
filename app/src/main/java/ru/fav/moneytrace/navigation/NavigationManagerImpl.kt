package ru.fav.moneytrace.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.fav.moneytrace.account.ui.AccountFeature
import ru.fav.moneytrace.base.state.TopAppBarState
import ru.fav.moneytrace.basefeature.FeatureBase
import ru.fav.moneytrace.expenses.ui.ExpensesFeature
import ru.fav.moneytrace.income.ui.IncomeFeature
import ru.fav.moneytrace.settings.ui.SettingsFeature
import ru.fav.moneytrace.stats.ui.StatsFeature
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor() : NavigationManager {

    val appFeatures: List<FeatureBase> = listOf(
        AccountFeature,
        SettingsFeature,
        IncomeFeature,
        ExpensesFeature,
        StatsFeature
    )

    override fun buildNavGraph(
        builder: NavGraphBuilder,
        navController: NavController,
        onTopAppBarStateChange: (TopAppBarState) -> Unit
    ) {
        appFeatures.forEach { feature ->
            feature.routes(builder, navController, this, onTopAppBarStateChange)
        }
    }

    override fun navigateToSettings(navController: NavController) {
        SettingsFeature.navigate(navController)
    }
    
    override fun navigateToIncome(navController: NavController) {
        IncomeFeature.navigate(navController)
    }

    override fun navigateToAccount(navController: NavController) {
        AccountFeature.navigate(navController)
    }

    override fun navigateToStats(navController: NavController) {
        StatsFeature.navigate(navController)
    }

    override fun navigateToExpenses(navController: NavController) {
        ExpensesFeature.navigate(navController)
    }
}
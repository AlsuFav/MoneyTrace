package ru.fav.moneytrace.navigation.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ru.fav.moneytrace.account.ui.AccountFeature
import ru.fav.moneytrace.base.R
import ru.fav.moneytrace.expenses.ui.ExpensesFeature
import ru.fav.moneytrace.income.ui.IncomeFeature
import ru.fav.moneytrace.navigation.BottomNavIds
import ru.fav.moneytrace.navigation.BottomNavItem
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.settings.ui.SettingsFeature
import ru.fav.moneytrace.stats.ui.StatsFeature
import kotlin.collections.forEach

@Composable
fun rememberBottomNavItems(
    navController: NavController,
    navigationManager: NavigationManager
): List<BottomNavItem> {
    return remember(navController, navigationManager) {
        listOf(
            BottomNavItem(
                id = BottomNavIds.Expenses.id,
                onClick = { navigationManager.navigateToExpenses(navController) },
                iconResourceId = R.drawable.ic_expenses,
                labelResourceId = R.string.expenses
            ),
            BottomNavItem(
                id = BottomNavIds.Income.id,
                onClick = { navigationManager.navigateToIncome(navController) },
                iconResourceId = R.drawable.ic_income,
                labelResourceId = R.string.income
            ),
            BottomNavItem(
                id = BottomNavIds.Account.id,
                onClick = { navigationManager.navigateToAccount(navController) },
                iconResourceId = R.drawable.ic_account,
                labelResourceId = R.string.account
            ),
            BottomNavItem(
                id = BottomNavIds.Stats.id,
                onClick = { navigationManager.navigateToStats(navController) },
                iconResourceId = R.drawable.ic_stats,
                labelResourceId = R.string.stats
            ),
            BottomNavItem(
                id = BottomNavIds.Settings.id,
                onClick = { navigationManager.navigateToSettings(navController) },
                iconResourceId = R.drawable.ic_settings,
                labelResourceId = R.string.settings
            )
        )
    }
}

@Composable
fun BottomNavigationBar(
    bottomNavItems: List<BottomNavItem>,
    currentRoute: String?
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val isSelected = when(item.id) {
                BottomNavIds.Account.id -> currentRoute?.startsWith(AccountFeature.navGraph.route) == true
                BottomNavIds.Income.id -> currentRoute?.startsWith(IncomeFeature.navGraph.route) == true
                BottomNavIds.Settings.id -> currentRoute?.startsWith(SettingsFeature.navGraph.route) == true
                BottomNavIds.Expenses.id -> currentRoute?.startsWith(ExpensesFeature.navGraph.route) == true
                BottomNavIds.Stats.id -> currentRoute?.startsWith(StatsFeature.navGraph.route) == true
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        item.onClick()
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResourceId),
                        contentDescription = stringResource(id = item.labelResourceId),
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.labelResourceId),
                    )
                },
                alwaysShowLabel = true,
            )
        }
    }
}


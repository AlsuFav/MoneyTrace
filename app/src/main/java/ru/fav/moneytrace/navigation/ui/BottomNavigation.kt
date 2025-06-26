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
import ru.fav.moneytrace.account.impl.ui.nav.AccountNav
import ru.fav.moneytrace.categories.ui.nav.CategoriesNav
import ru.fav.moneytrace.ui.R
import ru.fav.moneytrace.expenses.ui.nav.ExpensesNav
import ru.fav.moneytrace.income.ui.nav.IncomeNav
import ru.fav.moneytrace.navigation.BottomNavIds
import ru.fav.moneytrace.navigation.BottomNavItem
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.settings.ui.SettingsNav
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
                id = BottomNavIds.Categories.id,
                onClick = { navigationManager.navigateToCategories(navController) },
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
                BottomNavIds.Account.id -> currentRoute?.startsWith(AccountNav.navGraph.route) == true
                BottomNavIds.Income.id -> currentRoute?.startsWith(IncomeNav.navGraph.route) == true
                BottomNavIds.Settings.id -> currentRoute?.startsWith(SettingsNav.navGraph.route) == true
                BottomNavIds.Expenses.id -> currentRoute?.startsWith(ExpensesNav.navGraph.route) == true
                BottomNavIds.Categories.id -> currentRoute?.startsWith(CategoriesNav.navGraph.route) == true
                else -> false
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    item.onClick()
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
                        maxLines = 1
                    )
                },
                alwaysShowLabel = true,
            )
        }
    }
}


package ru.fav.moneytrace.account.impl.ui.nav

import ru.fav.moneytrace.navigation.NavGraph

class AccountNavGraph : NavGraph() {
    override val route = "account"
    override val startDestination = "account_main_screen"
    val updateDestination = "account_update_screen"
}
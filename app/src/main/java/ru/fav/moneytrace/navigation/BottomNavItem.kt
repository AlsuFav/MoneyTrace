package ru.fav.moneytrace.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomNavItem(
    val id: String,
    val onClick: () -> Unit,
    @DrawableRes val iconResourceId: Int,
    @StringRes val labelResourceId: Int
)
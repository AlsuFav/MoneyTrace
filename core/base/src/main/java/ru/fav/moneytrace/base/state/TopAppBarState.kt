package ru.fav.moneytrace.base.state

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class TopAppBarState (
    val isVisible: Boolean = false,
    val title: String? = null,
    val backgroundColor: Color? = null,
    val navigationIcon: @Composable (() -> Unit)? = null,
    val actions: @Composable (RowScope.() -> Unit) = {}
)
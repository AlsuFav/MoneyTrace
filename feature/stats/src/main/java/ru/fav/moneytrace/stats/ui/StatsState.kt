package ru.fav.moneytrace.stats.ui

import androidx.compose.runtime.Immutable
import ru.fav.moneytrace.domain.model.Stat

@Immutable
data class StatsState(
    val input: String = "",
    val stats: List<Stat> = emptyList()
)

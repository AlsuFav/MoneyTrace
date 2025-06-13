package ru.fav.moneytrace.stats.ui

sealed class StatsEvent {
    data class OnInputChanged(val input: String) : StatsEvent()
    object OnSearch : StatsEvent()
}

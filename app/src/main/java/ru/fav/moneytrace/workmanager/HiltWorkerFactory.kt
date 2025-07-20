package ru.fav.moneytrace.workmanager

import androidx.work.DelegatingWorkerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HiltWorkerFactory @Inject constructor() : DelegatingWorkerFactory() {
}
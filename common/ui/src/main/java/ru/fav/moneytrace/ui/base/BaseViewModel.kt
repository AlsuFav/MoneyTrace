package ru.fav.moneytrace.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Базовый ViewModel с поддержкой MVI архитектуры.
 *
 * Предоставляет структуру для управления состоянием (State), обработки событий (Event)
 * и побочных эффектов (Effect). Включает управление корутинами и автоматическую
 * очистку ресурсов при уничтожении ViewModel.
 *
 * @param State Тип состояния UI
 * @param Event Тип событий пользователя
 * @param Effect Тип побочных эффектов (показ сообщений и т.д.)
 */

abstract class BaseViewModel<State, Event, Effect> : ViewModel() {

    private val jobs = mutableSetOf<Job>()

    protected abstract val _state: MutableStateFlow<State>
    abstract val state: StateFlow<State>

    protected abstract val _effect: MutableSharedFlow<Effect>
    abstract val effect: SharedFlow<Effect>

    abstract fun reduce(event: Event)

    protected fun launchTask(block: suspend CoroutineScope.() -> Unit): Job {
        val job = viewModelScope.launch(block = block)
        jobs.add(job)
        job.invokeOnCompletion { jobs.remove(job) }
        return job
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun cancelAllTasks() {
        jobs.forEach { it.cancel() }
        jobs.clear()
    }

    override fun onCleared() {
        super.onCleared()
        cancelAllTasks()
    }
}
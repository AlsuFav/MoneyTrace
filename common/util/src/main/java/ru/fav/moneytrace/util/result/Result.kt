package ru.fav.moneytrace.util.result

sealed class Result<out T> {
    data class Success<out T>(val data: T, val cached: Boolean = false) : Result<T>()
    data class Failure(val reason: FailureReason) : Result<Nothing>()
}
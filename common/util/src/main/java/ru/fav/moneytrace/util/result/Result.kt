package ru.fav.moneytrace.util.result

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class HttpError(val reason: FailureReason) : Result<Nothing>()
    object NetworkError : Result<Nothing>()
}
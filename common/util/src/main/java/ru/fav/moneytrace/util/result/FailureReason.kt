package ru.fav.moneytrace.util.result

sealed class FailureReason {
    data class BadRequest(val message: String? = null) : FailureReason()
    data class Unauthorized(val message: String? = null) : FailureReason()
    data class Forbidden(val message: String? = null) : FailureReason()
    data class NotFound(val message: String? = null) : FailureReason()
    data class Conflict(val message: String? = null) : FailureReason()
    data class Server(val message: String? = null) : FailureReason()
    data class Unknown(val message: String? = null) : FailureReason()
}
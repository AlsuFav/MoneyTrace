package ru.fav.moneytrace.network.util

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.fav.moneytrace.domain.di.qualifier.IoDispatchers
import ru.fav.moneytrace.util.result.FailureReason
import ru.fav.moneytrace.util.result.Result
import java.io.IOException
import javax.inject.Inject

class ApiClient @Inject constructor(
    @IoDispatchers private val dispatcher: CoroutineDispatcher,
    private val gson: Gson
) {

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val RETRY_DELAY_MS = 2000L
    }

    suspend fun <T> call(
        apiRequest: suspend () -> T
    ): Result<T> {
        return withContext(dispatcher) {

            var lastException: Throwable? = null

            repeat(MAX_RETRY_ATTEMPTS) { attempt ->
                try {
                    return@withContext Result.Success(apiRequest())
                } catch (throwable: Throwable) {
                    lastException = throwable

                    when (throwable) {
                        is IOException -> return@withContext Result.NetworkError
                        is HttpException -> {
                            if (throwable.code() in 500..599 && attempt < MAX_RETRY_ATTEMPTS - 1) {
                                delay(RETRY_DELAY_MS)
                            } else {
                                return@withContext Result.HttpError(throwable.toFailureReason())
                            }
                        }
                        else -> return@withContext Result.HttpError(FailureReason.Unknown(throwable.message))
                    }
                }
            }

            when (lastException) {
                is HttpException -> Result.HttpError(lastException.toFailureReason())
                else -> Result.HttpError(FailureReason.Unknown(lastException?.message))
            }
        }
    }


    private fun HttpException.toFailureReason(): FailureReason {
        val serverMessage = getErrorMessageFromResponse()

        return when (code()) {
            400 -> FailureReason.BadRequest(serverMessage)
            401 -> FailureReason.Unauthorized(serverMessage)
            403 -> FailureReason.Forbidden(serverMessage)
            404 -> FailureReason.NotFound(serverMessage)
            409 -> FailureReason.Conflict(serverMessage)
            in 500..599 -> FailureReason.Server(serverMessage)
            else -> FailureReason.Unknown(serverMessage)
        }
    }

    private fun HttpException.getErrorMessageFromResponse(): String? {
        return try {
            val errorBody = response()?.errorBody()?.string()
            if (errorBody.isNullOrEmpty()) {
                return message()
            }

            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            errorResponse.error ?: message()
        } catch (_: Exception) {
            message()
        }
    }
}
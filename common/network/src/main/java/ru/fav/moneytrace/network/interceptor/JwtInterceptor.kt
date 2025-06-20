package ru.fav.moneytrace.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.fav.moneytrace.network.BuildConfig
import javax.inject.Inject

class JwtInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val jwt = BuildConfig.TOKEN
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $jwt")
            .build()
        return chain.proceed(request)
    }
}
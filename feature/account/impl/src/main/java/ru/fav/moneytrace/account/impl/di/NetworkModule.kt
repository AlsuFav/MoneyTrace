package ru.fav.moneytrace.account.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.moneytrace.account.impl.data.remote.AccountApi
import ru.fav.moneytrace.network.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAccountApi(
        retrofit: Retrofit,
    ): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }
}
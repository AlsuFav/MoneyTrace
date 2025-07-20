package ru.fav.moneytrace.transaction.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.moneytrace.network.BuildConfig
import ru.fav.moneytrace.transaction.impl.data.remote.TransactionApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideTransactionApi(
        retrofit: Retrofit,
    ): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }
}
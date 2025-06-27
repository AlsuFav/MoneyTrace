package ru.fav.moneytrace.account.impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.moneytrace.account.api.repository.AccountRepository
import ru.fav.moneytrace.account.impl.data.remote.AccountApi
import ru.fav.moneytrace.account.impl.data.repository.AccountRepositoryImpl
import ru.fav.moneytrace.network.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindAccountRepositoryToImpl(impl: AccountRepositoryImpl): AccountRepository
}

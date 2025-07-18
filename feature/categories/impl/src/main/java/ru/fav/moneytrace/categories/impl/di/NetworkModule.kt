package ru.fav.moneytrace.categories.impl.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fav.moneytrace.categories.impl.data.remote.CategoryApi
import ru.fav.moneytrace.network.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideCategoryApi(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): CategoryApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.MONEYTRACE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()

        return retrofit.create(CategoryApi::class.java)
    }
}
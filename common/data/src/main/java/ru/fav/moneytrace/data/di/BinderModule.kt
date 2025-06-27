package ru.fav.moneytrace.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.data.provider.ResourceProviderImpl
import ru.fav.moneytrace.domain.provider.ResourceProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BinderModule {

    @Binds
    @Singleton
    fun bindResourceProviderToImpl(impl: ResourceProviderImpl): ResourceProvider
}

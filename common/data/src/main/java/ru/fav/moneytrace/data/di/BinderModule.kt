package ru.fav.moneytrace.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.data.provider.DateProviderImpl
import ru.fav.moneytrace.data.repository.AccountRepositoryImpl
import ru.fav.moneytrace.data.repository.TransactionRepositoryImpl
import ru.fav.moneytrace.domain.provider.DateProvider
import ru.fav.moneytrace.domain.repository.AccountRepository
import ru.fav.moneytrace.domain.repository.TransactionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BinderModule {

    @Binds
    @Singleton
    fun bindAccountRepositoryToImpl(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindTransactionRepositoryToImpl(impl: TransactionRepositoryImpl): TransactionRepository


    @Binds
    @Singleton
    fun bindDateProviderToImpl(impl: DateProviderImpl): DateProvider

}

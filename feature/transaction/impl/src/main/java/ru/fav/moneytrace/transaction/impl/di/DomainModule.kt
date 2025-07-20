package ru.fav.moneytrace.transaction.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.categories.api.usecase.GetCategoriesUseCase
import ru.fav.moneytrace.transaction.api.usecase.GetTransactionsByPeriodUseCase
import ru.fav.moneytrace.transaction.impl.domain.usecase.GetTransactionsByPeriodUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindGetTransactionsByPeriodUseCaseToImpl(impl: GetTransactionsByPeriodUseCaseImpl): GetTransactionsByPeriodUseCase

}
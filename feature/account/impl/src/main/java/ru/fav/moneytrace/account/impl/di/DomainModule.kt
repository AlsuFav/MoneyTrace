package ru.fav.moneytrace.account.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.account.api.usecase.GetAccountUseCase
import ru.fav.moneytrace.account.impl.domain.usecase.GetAccountUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindGetAccountUseCaseToImpl(impl: GetAccountUseCaseImpl): GetAccountUseCase

}
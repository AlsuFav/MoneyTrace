package ru.fav.moneytrace.categories.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.categories.api.usecase.GetCategoriesUseCase
import ru.fav.moneytrace.categories.impl.domain.usecase.GetCategoriesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindGetCategoriesUseCaseToImpl(impl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

}
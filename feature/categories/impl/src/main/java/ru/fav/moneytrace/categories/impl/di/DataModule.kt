package ru.fav.moneytrace.categories.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.categories.api.repository.CategoryRepository
import ru.fav.moneytrace.categories.impl.data.remote.repository.CategoryRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindCategoryRepositoryToImpl(impl: CategoryRepositoryImpl): CategoryRepository
}

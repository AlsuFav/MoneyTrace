package ru.fav.moneytrace.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.fav.moneytrace.navigation.NavigationManager
import ru.fav.moneytrace.navigation.NavigationManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    @Singleton
    fun bindNavigationManagerToImpl(impl: NavigationManagerImpl): NavigationManager
}

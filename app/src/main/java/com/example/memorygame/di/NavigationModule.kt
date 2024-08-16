package com.example.memorygame.di

import com.example.memorygame.navigation.AppNavigationDispatcher
import com.example.memorygame.navigation.AppNavigationHandler
import com.example.memorygame.navigation.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl : AppNavigationDispatcher) : AppNavigator

    @Binds
    fun bindAppNavigationHandler(impl: AppNavigationDispatcher) : AppNavigationHandler
}
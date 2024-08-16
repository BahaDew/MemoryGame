package com.example.memorygame.di

import com.example.memorygame.domain.GameRepository
import com.example.memorygame.domain.impl.GameRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindRepository(impl : GameRepositoryImpl) : GameRepository
}
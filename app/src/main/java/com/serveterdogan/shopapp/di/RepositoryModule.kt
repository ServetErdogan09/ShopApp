package com.serveterdogan.shopapp.di

import com.serveterdogan.shopapp.data.repository.AuthRepositoryImpl
import com.serveterdogan.shopapp.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun provideRepositoryImpl(
        repositoryImpl: AuthRepositoryImpl
    ): AuthRepository


}
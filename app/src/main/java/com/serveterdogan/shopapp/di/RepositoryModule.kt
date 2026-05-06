package com.serveterdogan.shopapp.di

import com.serveterdogan.shopapp.data.repository.AuthRepositoryImpl
import com.serveterdogan.shopapp.data.repository.FavoriteRepositoryImpl
import com.serveterdogan.shopapp.data.repository.ProductRepositoryImpl
import com.serveterdogan.shopapp.data.repository.CartRepositoryImpl
import com.serveterdogan.shopapp.domain.repository.AuthRepository
import com.serveterdogan.shopapp.domain.repository.FavoriteRepository
import com.serveterdogan.shopapp.domain.repository.ProductRepository
import com.serveterdogan.shopapp.domain.repository.CartRepository
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

    @Binds
    @Singleton
    abstract fun provideProductRepositoryImpl(
        repositoryImpl: ProductRepositoryImpl
    ): ProductRepository


    @Binds
    @Singleton
    abstract fun provideFavoriteRepositoryImpl(
        repositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Binds
    @Singleton
    abstract fun provideCartRepositoryImpl(
        repositoryImpl: CartRepositoryImpl
    ): CartRepository

}
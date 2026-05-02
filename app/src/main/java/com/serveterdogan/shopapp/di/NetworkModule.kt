package com.serveterdogan.shopapp.di

import com.serveterdogan.shopapp.data.remote.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providerJson() : Json = Json{
        ignoreUnknownKeys = true
    }


    @Provides
    @Singleton
    @AppQualifiers.ReqResRetrofit
    fun provideReqResRetrofit(
        json: Json
    )  : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(
        @AppQualifiers.ReqResRetrofit retrofit: Retrofit
    ) : AuthApiService{
     return  retrofit.create(AuthApiService::class.java)
    }















}
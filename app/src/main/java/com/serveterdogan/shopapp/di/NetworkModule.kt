package com.serveterdogan.shopapp.di

import com.serveterdogan.shopapp.BuildConfig
import com.serveterdogan.shopapp.data.remote.service.AuthApiService
import com.serveterdogan.shopapp.data.remote.service.ProductApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                if (BuildConfig.DEBUG && originalRequest.url.host.contains("reqres.in")) {
                    requestBuilder.addHeader("x-api-key", BuildConfig.REQRES_API_KEY)
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    @AppQualifiers.ReqResRetrofit
    fun provideReqResRetrofit(
        json: Json,
        okHttpClient: OkHttpClient
    )  : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .client(okHttpClient)
            .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType()))
            .build()
    }




    @Provides
    @Singleton
    @AppQualifiers.DummyRetrofit
    fun provideDummyRetrofit(
        json: Json,
        okHttpClient: OkHttpClient
    )  : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .client(okHttpClient)
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


    @Provides
    @Singleton
    fun provideProductApiService(
       @AppQualifiers.DummyRetrofit retrofit: Retrofit
    ): ProductApiService{
        return retrofit.create(ProductApiService::class.java)
    }















}
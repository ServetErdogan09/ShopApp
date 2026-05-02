package com.serveterdogan.shopapp.di

import javax.inject.Qualifier

object AppQualifiers {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ReqResRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DummyRetrofit
}
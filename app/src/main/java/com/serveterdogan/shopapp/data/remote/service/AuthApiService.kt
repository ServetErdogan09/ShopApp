package com.serveterdogan.shopapp.data.remote.service

import com.serveterdogan.shopapp.data.remote.dto.LoginResponse
import com.serveterdogan.shopapp.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse


    @POST("register")
    suspend fun register(
        @Body request: LoginRequest // şimdilik bu model olsun
    ) : LoginResponse


}
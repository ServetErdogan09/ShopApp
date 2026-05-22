package com.serveterdogan.shopapp.domain.repository

import com.serveterdogan.shopapp.domain.model.LoginRequest

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest) : Result<String>

    suspend fun register(loginRequest: LoginRequest) : Result<String>
}
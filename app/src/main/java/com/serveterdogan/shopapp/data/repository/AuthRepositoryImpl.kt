package com.serveterdogan.shopapp.data.repository

import com.serveterdogan.shopapp.data.remote.service.AuthApiService
import com.serveterdogan.shopapp.di.AppQualifiers
import com.serveterdogan.shopapp.domain.model.LoginRequest
import com.serveterdogan.shopapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Result<String> {
     return  try {
          val response = apiService.login(loginRequest)
         Result.success(response.token)
       }catch (e: Exception){
         Result.failure(e)
       }
    }

    override suspend fun register(loginRequest: LoginRequest): Result<String> {
        return  try {
            val response = apiService.register(loginRequest)
            Result.success(response.token)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

}
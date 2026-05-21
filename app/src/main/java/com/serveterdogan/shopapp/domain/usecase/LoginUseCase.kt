package com.serveterdogan.shopapp.domain.usecase

import com.serveterdogan.shopapp.domain.model.LoginRequest
import com.serveterdogan.shopapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        request: LoginRequest
    ) : Result<String>{

      if(request.password.isEmpty() || request.email.isEmpty()){
          return Result.failure(Exception("Lütfen tüm alanları doldurun"))
      }

      if(request.password.length < 6){
          return Result.failure(Exception("Şifre en az 6 karakter olmalıdır"))
      }

      return  repository.login(request)

    }
}
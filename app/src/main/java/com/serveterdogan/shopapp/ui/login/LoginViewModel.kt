package com.serveterdogan.shopapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.data.local.TokenManager
import com.serveterdogan.shopapp.domain.model.LoginRequest
import com.serveterdogan.shopapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())

    val state: StateFlow<LoginState> = _state.asStateFlow()


    fun onEmailChange(email : String){
       _state.value = _state.value.copy(email = email)
    }


    fun onPasswordChange(password : String){
        _state.value = _state.value.copy(password = password)
    }


    fun userLogin(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true , error = null)


            val result = loginUseCase.invoke(
                LoginRequest(
                    email = _state.value.email,
                    password =_state.value.password
                )
            )

            result.onSuccess {
                tokenManager.saveToken(it) // succses ise tokını kaydet
                Log.d("LOGIN","Giriş başarılı : $it")
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = null,
                    isSuccess = true
                )
            }

            result.onFailure{
                Log.d("LOGIN","Giriş başarsız : $it")
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = it.message
                )
            }
        }
    }

}
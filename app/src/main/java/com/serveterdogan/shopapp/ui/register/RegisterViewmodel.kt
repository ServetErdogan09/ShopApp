package com.serveterdogan.shopapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.data.local.TokenManager
import com.serveterdogan.shopapp.domain.model.LoginRequest
import com.serveterdogan.shopapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewmodel @Inject constructor(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {



    private val _state = MutableStateFlow(RegisterState())
    val state : StateFlow<RegisterState> = _state.asStateFlow() // bu feature sadece dışarıdan okunulabilir



    fun onEmailChange(email : String){
        _state.value = _state.value.copy(email = email)
    }


    fun onPasswordChange(password : String){
        _state.value = _state.value.copy(password = password)
    }


    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = confirmPassword)
    }

    fun userRegister() {
        val email = _state.value.email.trim()
        val password = _state.value.password.trim()
        val confirmPassword = _state.value.confirmPassword.trim()


        if (email.isEmpty() || password.isEmpty()) {
            _state.value = _state.value.copy(error = "Lütfen tüm alanları doldurun")
            return
        }

        if (password != confirmPassword) {
            _state.value = _state.value.copy(error = "Şifreler eşleşmiyor!")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val result = repository.register(
                LoginRequest(
                    email = email,
                    password = password
                )
            )


                result.onSuccess{
                 tokenManager.saveToken(it)    // kayıt başarrılı ise tokenı data store kaydet
                  _state.value = _state.value.copy(
                      isLoading = false,
                      error = null,
                      isSuccess = true
                  )
                }


                result.onFailure {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = it.message,
                        isSuccess = false
                    )
                }

            }



    }


    fun clearToken(){
        viewModelScope.launch {
            tokenManager.clearToken()
        }
    }

}
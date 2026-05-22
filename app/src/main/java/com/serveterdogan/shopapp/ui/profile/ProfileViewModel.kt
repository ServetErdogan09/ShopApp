package com.serveterdogan.shopapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private  val tokenManager: TokenManager
)  : ViewModel(){

    fun logout(){
        viewModelScope.launch {
            tokenManager.clearToken()
        }
    }

}
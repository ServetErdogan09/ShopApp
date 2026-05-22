package com.serveterdogan.shopapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.data.local.TokenManager
import com.serveterdogan.shopapp.navigation.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tokenManager: TokenManager
)  : ViewModel(){

        val sessionState : StateFlow<SessionState> =
            tokenManager.tokenFlow
                .map {token->
                    delay(2000) // 2 saniye geç açıl
                    if(token.isNullOrEmpty()){
                        SessionState.LoggedOut
                    }else{
                        SessionState.LoggedIn
                    }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = SessionState.Loading // ilk değeri Loading olacak
                )

}
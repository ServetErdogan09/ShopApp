package com.serveterdogan.shopapp.navigation

sealed interface SessionState {
    object Loading : SessionState
    object LoggedIn : SessionState
    object LoggedOut : SessionState
}
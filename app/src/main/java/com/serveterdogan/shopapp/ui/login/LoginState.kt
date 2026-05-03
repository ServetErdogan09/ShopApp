package com.serveterdogan.shopapp.ui.login

import kotlinx.coroutines.flow.StateFlow

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

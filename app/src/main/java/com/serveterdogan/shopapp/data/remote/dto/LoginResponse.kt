package com.serveterdogan.shopapp.data.remote.dto

import kotlinx.serialization.Serializable

// api'den gelecek json dosyasını bu modele dönüştüreceğiz
@Serializable
data class LoginResponse(
    val token : String

)
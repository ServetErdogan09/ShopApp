package com.serveterdogan.shopapp.data.remote

import androidx.compose.ui.text.LinkAnnotation

// api'den gelecek json dosyasını bu modele dönüştüreceğiz
data class LoginResponse(
    val id : Int,
    val email : String,
    val username : String,
    val firstName : String,
    val lastName : String,
    val image : String,
    val accessToken : String,
    val refreshToken : String

)

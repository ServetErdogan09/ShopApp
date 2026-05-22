package com.serveterdogan.shopapp.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serveterdogan.shopapp.ui.login.LuxeTextField
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@Composable
fun RegisterScreen(
    state: RegisterState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onBackToLoginClick: () -> Unit
) {
    val gradientBrush = Brush.radialGradient(
        colors = listOf(LuxeColors.GradientStart, LuxeColors.GradientEnd, LuxeColors.Background),
        radius = 1500f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back Button & Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackToLoginClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = LuxeColors.Primary)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "LUXE",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LuxeColors.Primary,
                letterSpacing = 8.sp
            )
            Text(
                text = "YENİ BİR DENEYİME BAŞLA",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = LuxeColors.Outline,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Glass Card Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.White.copy(alpha = 0.4f))
                    .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(32.dp))
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Hesap Oluştur",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LuxeColors.OnSurface
                    )
                    Text(
                        text = "Premium alışveriş dünyasına katılmak için bilgilerinizi girin.",
                        fontSize = 14.sp,
                        color = LuxeColors.Outline,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                    )

                    // Email Field
                    LuxeTextField(
                        value = state.email,
                        onValueChange = onEmailChange,
                        label = "E-POSTA ADRESİ",
                        placeholder = "isim@ornek.com",
                        icon = Icons.Default.Email,
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Field
                    var passwordVisible by remember { mutableStateOf(false) }
                    LuxeTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        label = "ŞİFRE",
                        placeholder = "••••••••",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordToggle = { passwordVisible = !passwordVisible }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password Field
                    var confirmPasswordVisible by remember { mutableStateOf(false) }
                    LuxeTextField(
                        value = state.confirmPassword,
                        onValueChange = onConfirmPasswordChange,
                        label = "ŞİFREYİ ONAYLA",
                        placeholder = "••••••••",
                        icon = Icons.Default.Lock,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        passwordVisible = confirmPasswordVisible,
                        onPasswordToggle = { confirmPasswordVisible = !confirmPasswordVisible }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Register Button
                    Button(
                        onClick = onRegisterClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Primary),
                        enabled = !state.isLoading
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = "KAYDOL",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            )
                        }
                    }

                    // Error Message
                    state.error?.let {
                        Text(
                            text = it,
                            color = LuxeColors.Error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Login Link
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Text(text = "Zaten hesabınız var mı?", fontSize = 14.sp, color = LuxeColors.Outline)
                Text(
                    text = "Giriş Yap",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxeColors.Primary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { onBackToLoginClick() }
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
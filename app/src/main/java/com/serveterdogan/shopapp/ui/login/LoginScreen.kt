package com.serveterdogan.shopapp.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@Composable
fun LoginScreen(
    state: LoginState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Brand Logo Section
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "LUXE",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = LuxeColors.Primary,
                letterSpacing = 8.sp
            )
            Text(
                text = "PREMİUM ALIŞVERİŞ DENEYİMİ",
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
                    .background(LuxeColors.SurfaceContainer.copy(alpha = 0.8f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(32.dp))
                    .padding(24.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Tekrar Hoş Geldiniz",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LuxeColors.OnSurface
                    )
                    Text(
                        text = "Hesabınıza erişmek için lütfen bilgilerinizi girin.",
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
                        onPasswordToggle = { passwordVisible = !passwordVisible },
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login Button
                    Button(
                        onClick = onLoginClick,
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
                                text = "GİRİŞ YAP",
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
                            modifier = Modifier.padding(top = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                }
            }

            // Register Link
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Text(text = "Hesabınız yok mu?", fontSize = 14.sp, color = LuxeColors.Outline)
                Text(
                    text = "Kaydol",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LuxeColors.Primary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clickable { onRegisterClick() }
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LuxeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: () -> Unit = {},
    trailingContent: @Composable (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = LuxeColors.OutlineVariant,
                letterSpacing = 1.sp
            )
            trailingContent?.invoke()
        }
        
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            placeholder = { Text(placeholder, fontSize = 14.sp, color = LuxeColors.OutlineVariant) },
            leadingIcon = { Icon(icon, contentDescription = null, tint = LuxeColors.Outline) },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = onPasswordToggle) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = LuxeColors.Outline
                        )
                    }
                }
            } else null,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LuxeColors.SurfaceContainerLow,
                unfocusedContainerColor = LuxeColors.SurfaceContainerLow,
                disabledContainerColor = LuxeColors.SurfaceContainerLow,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}

@Composable
fun SocialButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, LuxeColors.OutlineVariant.copy(alpha = 0.3f)),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
    ) {
        Text(text = text, color = LuxeColors.OnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun Row(modifier: Modifier = Modifier, gap: androidx.compose.ui.unit.Dp, content: @Composable RowScope.() -> Unit) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(gap), content = content)
}

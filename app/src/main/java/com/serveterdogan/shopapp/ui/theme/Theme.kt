package com.serveterdogan.shopapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ShopAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val luxeColors = if (darkTheme) DarkLuxeColors else LightLuxeColors
    
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = luxeColors.primary,
            onPrimary = luxeColors.onPrimary,
            background = luxeColors.background,
            onBackground = luxeColors.onBackground,
            surface = luxeColors.surface,
            onSurface = luxeColors.onSurface
        )
    } else {
        lightColorScheme(
            primary = luxeColors.primary,
            onPrimary = luxeColors.onPrimary,
            background = luxeColors.background,
            onBackground = luxeColors.onBackground,
            surface = luxeColors.surface,
            onSurface = luxeColors.onSurface
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window
        window.statusBarColor = luxeColors.background.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }

    CompositionLocalProvider(LocalLuxeColors provides luxeColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
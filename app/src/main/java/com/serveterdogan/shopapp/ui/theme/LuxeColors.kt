package com.serveterdogan.shopapp.ui.theme

import androidx.compose.ui.graphics.Color

object LuxeColors {
    // Core Colors
    val Primary = Color(0xFF00535B)
    val OnPrimary = Color(0xFFFFFFFF)
    val PrimaryContainer = Color(0xFF006D77)
    val OnPrimaryContainer = Color(0xFF9BECF7)
    
    val Secondary = Color(0xFFA23E21)
    val OnSecondary = Color(0xFFFFFFFF)
    val SecondaryContainer = Color(0xFFFE825F)
    val OnSecondaryContainer = Color(0xFF721C01)
    
    val Tertiary = Color(0xFF3829CF)
    val OnTertiary = Color(0xFFFFFFFF)
    val TertiaryContainer = Color(0xFF5149E7)
    val OnTertiaryContainer = Color(0xFFDEDBFF)
    
    val Background = Color(0xFFFBF8FF)
    val OnBackground = Color(0xFF1A1B22)
    
    val Surface = Color(0xFFFBF8FF)
    val OnSurface = Color(0xFF1A1B22)
    val SurfaceVariant = Color(0xFFE3E1EC)
    val OnSurfaceVariant = Color(0xFF3E494A)
    
    val SurfaceContainerLow = Color(0xFFF4F2FD)
    val SurfaceContainer = Color(0xFFEEEDF7)
    val SurfaceContainerHigh = Color(0xFFE8E7F1)
    val SurfaceContainerHighest = Color(0xFFE3E1EC)
    
    val Outline = Color(0xFF6F797A)
    val OutlineVariant = Color(0xFFBEC8CA)
    
    val Error = Color(0xFFBA1A1A)
    val ErrorContainer = Color(0xFFFFDAD6)
    val OnError = Color(0xFFFFFFFF)
    val OnErrorContainer = Color(0xFF93000A)
    
    val Success = Color(0xFF006972)

    // Design Accents
    val GlassBackground = Color(0xFFFFFFFF).copy(alpha = 0.6f)
    val GlassHeader = Color(0xFFFBF8FF).copy(alpha = 0.7f)
    val LumeBorder = Color(0x0D000000) // 0.05 alpha black
    
    val GradientStart = Color(0xFF006972).copy(alpha = 0.05f)
    val GradientEnd = Color(0xFFFBF8FF)

    // Dark Theme Colors
    val PrimaryDark = Color(0xFF82D3DE)
    val OnPrimaryDark = Color(0xFF00363D)
    val BackgroundDark = Color(0xFF1A1B22)
    val OnBackgroundDark = Color(0xFFE3E1EC)
    val SurfaceDark = Color(0xFF1A1B22)
    val OnSurfaceDark = Color(0xFFE3E1EC)
}

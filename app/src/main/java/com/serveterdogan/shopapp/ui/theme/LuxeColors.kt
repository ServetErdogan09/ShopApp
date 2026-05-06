package com.serveterdogan.shopapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class LuxeColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val outline: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val glassHeader: Color,
    val glassBackground: Color,
    val lumeBorder: Color,
    val error: Color
)

val DarkLuxeColors = LuxeColorScheme(
    primary = Color(0xFF57F1DB),
    onPrimary = Color(0xFF003731),
    secondary = Color(0xFFFFB2B9),
    onSecondary = Color(0xFF67001F),
    secondaryContainer = Color(0xFF891933),
    onSecondaryContainer = Color(0xFFFF97A3),
    background = Color(0xFF0B1326),
    onBackground = Color(0xFFDAE2FD),
    surface = Color(0xFF0B1326),
    onSurface = Color(0xFFDAE2FD),
    surfaceContainer = Color(0xFF171F33),
    surfaceContainerHigh = Color(0xFF222A3D),
    outline = Color(0xFF859490),
    gradientStart = Color(0xFF131B2E),
    gradientEnd = Color(0xFF0B1326),
    glassHeader = Color(0xFF0B1326).copy(alpha = 0.8f),
    glassBackground = Color(0xFF2D3449).copy(alpha = 0.15f),
    lumeBorder = Color(0xFF3C4A46).copy(alpha = 0.5f),
    error = Color(0xFFFFB4AB)
)

val LightLuxeColors = LuxeColorScheme(
    primary = Color(0xFF006A60),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFBA1A1A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD6),
    onSecondaryContainer = Color(0xFF410002),
    background = Color(0xFFF0F4FF),
    onBackground = Color(0xFF191C1E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF191C1E),
    surfaceContainer = Color(0xFFE6EEFF).copy(alpha = 0.9f),
    surfaceContainerHigh = Color(0xFFD9E2FF),
    outline = Color(0xFF707976),
    gradientStart = Color(0xFFF8F9FF),
    gradientEnd = Color(0xFFE6EEFF),
    glassHeader = Color(0xFFFFFFFF).copy(alpha = 0.7f),
    glassBackground = Color(0xFFFFFFFF).copy(alpha = 0.4f),
    lumeBorder = Color(0xFF006A60).copy(alpha = 0.1f),
    error = Color(0xFFBA1A1A)
)

val LocalLuxeColors = staticCompositionLocalOf { DarkLuxeColors }

object LuxeColors {
    val Primary @Composable get() = LocalLuxeColors.current.primary
    val OnPrimary @Composable get() = LocalLuxeColors.current.onPrimary
    val Secondary @Composable get() = LocalLuxeColors.current.secondary
    val OnSecondary @Composable get() = LocalLuxeColors.current.onSecondary
    val SecondaryContainer @Composable get() = LocalLuxeColors.current.secondaryContainer
    val OnSecondaryContainer @Composable get() = LocalLuxeColors.current.onSecondaryContainer
    val Background @Composable get() = LocalLuxeColors.current.background
    val OnBackground @Composable get() = LocalLuxeColors.current.onBackground
    val Surface @Composable get() = LocalLuxeColors.current.surface
    val OnSurface @Composable get() = LocalLuxeColors.current.onSurface
    val SurfaceContainer @Composable get() = LocalLuxeColors.current.surfaceContainer
    val SurfaceContainerHigh @Composable get() = LocalLuxeColors.current.surfaceContainerHigh
    val Outline @Composable get() = LocalLuxeColors.current.outline
    val GradientStart @Composable get() = LocalLuxeColors.current.gradientStart
    val GradientEnd @Composable get() = LocalLuxeColors.current.gradientEnd
    val GlassHeader @Composable get() = LocalLuxeColors.current.glassHeader
    val GlassBackground @Composable get() = LocalLuxeColors.current.glassBackground
    val LumeBorder @Composable get() = LocalLuxeColors.current.lumeBorder
    val Error @Composable get() = LocalLuxeColors.current.error
    
    val SurfaceContainerLow @Composable get() = LocalLuxeColors.current.surfaceContainer
    val SurfaceContainerHighest @Composable get() = LocalLuxeColors.current.surfaceContainerHigh
    val OnSurfaceVariant @Composable get() = LocalLuxeColors.current.outline
    val OutlineVariant @Composable get() = LocalLuxeColors.current.outline
}

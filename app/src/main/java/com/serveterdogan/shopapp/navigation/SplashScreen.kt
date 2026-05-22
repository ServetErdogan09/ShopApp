package com.serveterdogan.shopapp.navigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@Composable
fun SplashScreen() {
    val backgroundGradient = Brush.radialGradient(
        colors = listOf(LuxeColors.GradientStart, LuxeColors.GradientEnd),
        radius = 2000f
    )

    val infiniteTransition = rememberInfiniteTransition(label = "logo_pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo_scale"
    )

    val progress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale),
                shape = CircleShape,
                color = LuxeColors.Primary.copy(alpha = 0.1f),
                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(LuxeColors.Primary, LuxeColors.Secondary)
                    )
                )
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Luxe Shop Logo",
                        modifier = Modifier.size(64.dp),
                        tint = LuxeColors.Primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "LUXE SHOP",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = LuxeColors.Primary,
                letterSpacing = 6.sp
            )

            Text(
                text = "Premium Shopping Experience",
                fontSize = 12.sp,
                color = LuxeColors.Outline,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(56.dp))

            LinearProgressIndicator(
            progress = { progress.value },
            modifier = Modifier
                                .width(160.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(8.dp)),
            color = LuxeColors.Primary,
            trackColor = LuxeColors.Primary.copy(alpha = 0.15f),
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )
        }
    }
}

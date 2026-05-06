package com.serveterdogan.shopapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serveterdogan.shopapp.ui.theme.LuxeColors
import com.serveterdogan.shopapp.ui.theme.ThemeViewModel
import androidx.activity.ComponentActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {
    // ÖNEMLİ: ViewModel'ı Activity seviyesinde alıyoruz ki MainActivity ile aynı instance olsun
    val context = LocalContext.current as ComponentActivity
    val themeViewModel: ThemeViewModel = hiltViewModel(context)
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    val backgroundGradient = Brush.radialGradient(
        colors = listOf(LuxeColors.GradientStart, LuxeColors.GradientEnd),
        radius = 2000f
    )

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                LuxeColors.Background.copy(alpha = 0.85f),
                                Color.Transparent
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "PROFİLİM",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = LuxeColors.Primary,
                        letterSpacing = 2.sp
                    )
                }
            }
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(backgroundGradient)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Header
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = LuxeColors.Primary.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LuxeColors.Primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Emily Selman",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = LuxeColors.OnSurface
            )
            Text(
                text = "emily.selman@example.com",
                fontSize = 14.sp,
                color = LuxeColors.Outline
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Settings Group
            Text(
                text = "UYGULAMA AYARLARI",
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = LuxeColors.Primary,
                letterSpacing = 1.sp
            )
            
            // SADECE KARANLIK MOD
            SettingsRow(
                icon = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                title = "Karanlık Mod",
                trailing = {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { themeViewModel.toggleTheme() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = LuxeColors.Primary,
                            checkedTrackColor = LuxeColors.Primary.copy(alpha = 0.5f)
                        )
                    )
                }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Logout Button
            Button(
                onClick = { onLogout() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LuxeColors.Error.copy(alpha = 0.1f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, LuxeColors.Error.copy(alpha = 0.2f))
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, tint = LuxeColors.Error)
                Spacer(modifier = Modifier.width(8.dp))
                Text("ÇIKIŞ YAP", color = LuxeColors.Error, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    title: String,
    trailing: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        color = LuxeColors.SurfaceContainer.copy(alpha = 0.5f),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = LuxeColors.SurfaceContainerHigh
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, modifier = Modifier.size(20.dp), tint = LuxeColors.Primary)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = title, fontSize = 16.sp, color = LuxeColors.OnSurface, fontWeight = FontWeight.Medium)
            }
            trailing()
        }
    }
}
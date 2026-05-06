package com.serveterdogan.shopapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.serveterdogan.shopapp.ui.theme.LuxeColors

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val items = Screen.bottomNavList
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Cam (glass) efekti için arka planı yarı saydam yapıyoruz
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        LuxeColors.Background.copy(alpha = 0.85f)
                    )
                )
            )
            .navigationBarsPadding()
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.height(56.dp)
        ) {
            items.forEach { screen ->
                val selected = currentRoute == screen.route

                NavigationBarItem(
                    icon = {
                        screen.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = screen.title,
                                tint = if (selected) LuxeColors.Primary else LuxeColors.OutlineVariant
                            )
                        }
                    },
                    label = {
                        Text(
                            text = screen.title,
                            color = if (selected) LuxeColors.Primary else LuxeColors.OutlineVariant,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (!selected) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = LuxeColors.Primary.copy(alpha = 0.15f),
                        selectedIconColor = LuxeColors.Primary,
                        unselectedIconColor = LuxeColors.OutlineVariant,
                        selectedTextColor = LuxeColors.Primary,
                        unselectedTextColor = LuxeColors.OutlineVariant
                    )
                )
            }
        }
    }
}

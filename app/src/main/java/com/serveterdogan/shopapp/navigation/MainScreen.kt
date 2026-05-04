package com.serveterdogan.shopapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serveterdogan.shopapp.ui.cart.CartScreen
import com.serveterdogan.shopapp.ui.favorite.FavoriteScreen
import com.serveterdogan.shopapp.ui.home.HomeScreen
import com.serveterdogan.shopapp.ui.profile.ProfileScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.Favorite.route) {
                    FavoriteScreen()
                }
                composable(Screen.Cart.route) {
                    CartScreen()
                }
                composable(Screen.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}
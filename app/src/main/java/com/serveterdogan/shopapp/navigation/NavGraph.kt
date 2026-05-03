package com.serveterdogan.shopapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serveterdogan.shopapp.ui.cart.CartScreen
import com.serveterdogan.shopapp.ui.favorite.FavoriteScreen
import com.serveterdogan.shopapp.ui.home.HomeScreen
import com.serveterdogan.shopapp.ui.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
       startDestination = Screen.Home.route,
        navController = navController
    ){
        composable(Screen.Home.route) {
            HomeScreen()
        }


        composable(Screen.Favorite.route){
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
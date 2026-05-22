package com.serveterdogan.shopapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route : String , val title : String = "" , val icon : ImageVector? = null) {
    object Login : Screen("login_page")

    object Register : Screen("register_page")
    object Main : Screen("main_page")
     object Home : Screen("home_page","Home", Icons.Default.Storefront)
    object Favorite : Screen("favorite_page","Favorite", Icons.Default.Favorite)
    object Cart : Screen("cart_page","Cart", Icons.Default.ShoppingBag)
    object Profile : Screen("profile_page","Profile", Icons.Default.Person)
    
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: Int) : String{
            return "product_details/$productId"
        }
    }

    companion object {
        val bottomNavList = listOf(
            Home,
            Favorite,
            Cart,
            Profile
        )
    }
}

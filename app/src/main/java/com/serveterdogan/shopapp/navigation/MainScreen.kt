package com.serveterdogan.shopapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serveterdogan.shopapp.ui.cart.CartScreen
import com.serveterdogan.shopapp.ui.favorite.FavoriteScreen
import com.serveterdogan.shopapp.ui.home.ProductScreen
import com.serveterdogan.shopapp.ui.home.ProductViewModel
import com.serveterdogan.shopapp.ui.profile.ProfileScreen

@Composable
fun MainScreen(rootNavController: NavHostController) {
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
                    val productViewModel : ProductViewModel  = hiltViewModel()
                    val product = productViewModel.productState.collectAsStateWithLifecycle()

                    ProductScreen(
                        state = product.value,
                        onSearch = productViewModel::getSearchProduct,
                        onCategoryClick = productViewModel::getProductCategory,
                        onProductClick = { productId ->
                            rootNavController.navigate(Screen.ProductDetails.createRoute(productId))
                        }
                    )
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
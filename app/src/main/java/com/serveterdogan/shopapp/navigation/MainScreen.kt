package com.serveterdogan.shopapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serveterdogan.shopapp.ui.cart.CartScreen
import com.serveterdogan.shopapp.ui.favorite.FavoriteScreen
import com.serveterdogan.shopapp.ui.favorite.FavoriteViewModel
import com.serveterdogan.shopapp.ui.home.ProductScreen
import com.serveterdogan.shopapp.ui.home.ProductViewModel
import com.serveterdogan.shopapp.ui.profile.ProfileScreen
import com.serveterdogan.shopapp.ui.profile.ProfileViewModel
import com.serveterdogan.shopapp.ui.register.RegisterViewmodel

@Composable
fun MainScreen(rootNavController: NavHostController) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0)
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
                        onFavoriteClick = productViewModel::toggleFavorite,
                        onProductClick = { productId ->
                            rootNavController.navigate(Screen.ProductDetails.createRoute(productId))
                        }
                    )
                }
                composable(Screen.Favorite.route) {
                    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
                    val favoriteState = favoriteViewModel.state.collectAsStateWithLifecycle()

                    FavoriteScreen(
                        state = favoriteState.value,
                        onProductClick = { productId ->
                            rootNavController.navigate(Screen.ProductDetails.createRoute(productId))
                        },
                        onRemoveFavorite = { productId ->
                            favoriteViewModel.deleteProductFavorite(productId)
                        }
                    )
                }
                composable(Screen.Cart.route) {
                    val cartViewModel: com.serveterdogan.shopapp.ui.cart.CartViewModel = hiltViewModel()
                    val cartState = cartViewModel.state.collectAsStateWithLifecycle()

                    CartScreen(
                        state = cartState.value,
                        onIncrement = cartViewModel::incrementQuantity,
                        onDecrement = cartViewModel::decrementQuantity,
                        onRemove = cartViewModel::removeFromCart,
                        onCheckoutClick = { /* Ödeme sayfası henüz yok */ }
                    )
                }
                composable(Screen.Profile.route) {
                    val viewmodel : ProfileViewModel = hiltViewModel()
                    ProfileScreen(
                        onLogout = {
                            viewmodel.logout() // çıkış yap
                        }
                    )
                }
            }
        }
    }
}
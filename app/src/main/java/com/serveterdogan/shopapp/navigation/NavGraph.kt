package com.serveterdogan.shopapp.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.serveterdogan.shopapp.ui.cart.CartViewModel
import com.serveterdogan.shopapp.ui.home.ProductDetailViewModel
import com.serveterdogan.shopapp.ui.home.ProductDetailsScreen
import com.serveterdogan.shopapp.ui.login.LoginScreen
import com.serveterdogan.shopapp.ui.login.LoginViewModel
import com.serveterdogan.shopapp.ui.main.MainViewModel
import com.serveterdogan.shopapp.ui.register.RegisterScreen
import com.serveterdogan.shopapp.ui.register.RegisterViewmodel

@Composable
fun NavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val sessionState by mainViewModel.sessionState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(sessionState) {
        if (sessionState is SessionState.LoggedOut) {
            val currentRoute = navController.currentDestination?.route
            if (currentRoute != Screen.Login.route && currentRoute != Screen.Register.route) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->
        when (sessionState) {
            is SessionState.Loading -> {
                SplashScreen()
            }
            else -> {
                NavHost(
                    startDestination = if (sessionState is SessionState.LoggedIn) Screen.Main.route else Screen.Login.route,
                    navController = navController,
                    modifier = Modifier.padding(paddingValues)
                ) {
                    composable(Screen.Login.route) {
                        val viewModel: LoginViewModel = hiltViewModel()
                        val state = viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(state.value.isSuccess) {
                            if (state.value.isSuccess) {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        }

                        LoginScreen(
                            state = state.value,
                            onEmailChange = viewModel::onEmailChange,
                            onPasswordChange = viewModel::onPasswordChange,
                            onLoginClick = viewModel::userLogin,
                            onRegisterClick = {
                                navController.navigate(Screen.Register.route)
                            }
                        )
                    }

                    composable(Screen.Register.route) {
                        val registerViewModel: RegisterViewmodel = hiltViewModel()
                        val state = registerViewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(state.value.isSuccess) {
                            if (state.value.isSuccess) {
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Login.route) { inclusive = true }
                                }
                            }
                        }

                        RegisterScreen(
                            state = state.value,
                            onEmailChange = registerViewModel::onEmailChange,
                            onPasswordChange = registerViewModel::onPasswordChange,
                            onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange,
                            onRegisterClick = registerViewModel::userRegister,
                            onBackToLoginClick = { navController.popBackStack() }
                        )
                    }

                    composable(Screen.Main.route) {
                        MainScreen(rootNavController = navController)
                    }

                    composable(
                        route = Screen.ProductDetails.route,
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                        val productViewModel: ProductDetailViewModel = hiltViewModel()
                        val state = productViewModel.productState.collectAsStateWithLifecycle()

                        LaunchedEffect(productId) {
                            productViewModel.getProductById(productId)
                        }

                        val cartViewModel: CartViewModel = hiltViewModel()

                        LaunchedEffect(cartViewModel.uiEvent) {
                            cartViewModel.uiEvent.collect { message ->
                                snackbarHostState.showSnackbar(message)
                            }
                        }

                        ProductDetailsScreen(
                            product = state.value.selectedProduct,
                            onBackClick = { navController.popBackStack() },
                            onAddToCartClick = cartViewModel::addToCart,
                            onFavoriteClick = productViewModel::toggleFavorite
                        )
                    }
                }
            }
        }
    }
}



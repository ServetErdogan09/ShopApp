package com.serveterdogan.shopapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.serveterdogan.shopapp.data.local.TokenManager
import com.serveterdogan.shopapp.ui.cart.CartScreen
import com.serveterdogan.shopapp.ui.favorite.FavoriteScreen
import com.serveterdogan.shopapp.ui.home.HomeScreen
import com.serveterdogan.shopapp.ui.login.LoginScreen
import com.serveterdogan.shopapp.ui.login.LoginViewModel
import com.serveterdogan.shopapp.ui.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: LoginViewModel,
    tokenManager: TokenManager
) {

    val token = tokenManager.tokenFlow.collectAsStateWithLifecycle(initialValue = null)
    NavHost(
       startDestination = if(token.value.isNullOrEmpty()) Screen.Login.route else Screen.Main.route,
        navController = navController
    ){


        composable(Screen.Login.route) {
            val state = viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(state.value.isSuccess) {
                if (state.value.isSuccess){
                    Log.d("HOME","Burası çaılıştı")
                    navController.navigate(Screen.Main.route){
                        // geri tuşuna basıldığı zaman bir daha login ekranına gelme
                        popUpTo(Screen.Login.route) { inclusive = true  }
                    }
                }
            }


            LoginScreen(
                state = state.value,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange,
                onLoginClick = viewModel::userLogin,
                onRegisterClick = { /* Şimdilik boş */ },
                onForgotPasswordClick = { /* Şimdilik boş */ },
            )
        }


        composable(Screen.Main.route) {
            MainScreen()
        }




    }




}
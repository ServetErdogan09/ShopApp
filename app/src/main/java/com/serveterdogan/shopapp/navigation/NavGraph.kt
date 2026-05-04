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
import com.serveterdogan.shopapp.ui.login.LoginScreen
import com.serveterdogan.shopapp.ui.login.LoginViewModel
import com.serveterdogan.shopapp.ui.register.RegisterScreen
import com.serveterdogan.shopapp.ui.register.RegisterViewmodel


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
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onForgotPasswordClick = {
                    // Şimdilik boş
                },
            )
        }

        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewmodel = hiltViewModel()
            val state = registerViewModel.state.collectAsStateWithLifecycle()

            // Kayıt başarılıysa ana sayfaya git
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
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }


        composable(Screen.Main.route) {
            MainScreen()
        }




    }




}
package com.serveterdogan.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serveterdogan.shopapp.navigation.MainScreen
import com.serveterdogan.shopapp.ui.login.LoginViewModel
import com.serveterdogan.shopapp.ui.theme.ShopAppTheme
import com.serveterdogan.shopapp.data.local.TokenManager
import com.serveterdogan.shopapp.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: com.serveterdogan.shopapp.ui.theme.ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsStateWithLifecycle()

            ShopAppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val viewModel: LoginViewModel = hiltViewModel()
                NavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    tokenManager = tokenManager,
                )
            }
        }
    }
}


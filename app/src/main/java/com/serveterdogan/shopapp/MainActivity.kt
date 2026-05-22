package com.serveterdogan.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.serveterdogan.shopapp.navigation.NavGraph
import com.serveterdogan.shopapp.ui.theme.ShopAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: com.serveterdogan.shopapp.ui.theme.ThemeViewModel = hiltViewModel()
            val isDarkThemeState by themeViewModel.isDarkTheme.collectAsStateWithLifecycle()
            val isDarkTheme = isDarkThemeState ?: isSystemInDarkTheme()

            ShopAppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController
                )
            }
        }
    }
}

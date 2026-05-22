package com.serveterdogan.shopapp.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serveterdogan.shopapp.data.local.ThemeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeManager : ThemeManager
) : ViewModel() {

     val isDarkTheme = themeManager.isDarkThemeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null // henüz yüklenemediği için başlangıçta null
    )


    fun toggleTheme(currentThemeState : Boolean){
       viewModelScope.launch {
           themeManager.saveThemePreference(currentThemeState)
       }
    }
}

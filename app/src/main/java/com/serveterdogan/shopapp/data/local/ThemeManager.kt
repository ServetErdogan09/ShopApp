package com.serveterdogan.shopapp.data.local


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton // bundan sadece bir nesne oluşturulacak
class ThemeManager @Inject constructor(
  val  dataStore: DataStore<Preferences>
) {


    // key oluşturuduk
    private val DARK_MODE_KEY =
        booleanPreferencesKey("is_dark_mode")

    // default olarak dark yaptık
    val isDarkThemeFlow: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[DARK_MODE_KEY] ?: true
        }


    // kullanıcı değiştiridiği zaman mode'I kaydet
    suspend fun saveThemePreference(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDark
        }
    }

}
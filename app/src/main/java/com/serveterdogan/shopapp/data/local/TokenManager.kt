package com.serveterdogan.shopapp.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class TokenManager @Inject constructor(
  val  dataStore: DataStore<Preferences>
) {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    // Tokenı kaydet
    suspend fun saveToken(token: String) {
       dataStore .edit { preferences ->
            preferences[TOKEN_KEY] = token
        }

        Log.d("TOKEN_KEY","Kaydedilen token key : $token")
    }

    // tokunu akış olarak sadece oku
    val tokenFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }

    // Tokenı sil çıkış yaptığında
    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
        Log.d("TOKEN_KEY","Tokın silindi")
    }
}

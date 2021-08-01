package com.zk.base_project.utils

import android.content.Context
import androidx.core.content.edit
import com.zk.base_project.BuildConfig
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val TOKEN_PREF = "token"
private const val PROFILE_PREF = "profile"

@Singleton
class PreferencesHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {

    private val preferences by lazy {
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_prefs", Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        preferences.edit { putString(TOKEN_PREF, token) }
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN_PREF, null)
    }

    fun clearToken() {
        preferences.edit { remove(TOKEN_PREF) }
    }

    fun clearProfile() {
        preferences.edit { remove(PROFILE_PREF) }
    }

    fun clearAllPrefs(){
        preferences.edit{
            clear()
        }
    }
}

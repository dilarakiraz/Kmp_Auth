package com.dilara.kmp_auth.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

actual object CredentialStorage {
    private const val PREFS_NAME = "auth_credentials"
    private const val KEY_EMAIL = "email"
    private const val KEY_PASSWORD = "password"
    private const val KEY_BIOMETRIC_ENABLED = "biometric_enabled"

    private fun getEncryptedPrefs(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    actual fun saveCredentials(context: Any, email: String, password: String) {
        val androidContext = context as? Context ?: return
        val prefs = getEncryptedPrefs(androidContext)
        prefs.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_BIOMETRIC_ENABLED, true)
            .apply()
    }

    actual fun getEmail(context: Any): String? {
        val androidContext = context as? Context ?: return null
        val prefs = getEncryptedPrefs(androidContext)
        return prefs.getString(KEY_EMAIL, null)
    }

    actual fun getPassword(context: Any): String? {
        val androidContext = context as? Context ?: return null
        val prefs = getEncryptedPrefs(androidContext)
        return prefs.getString(KEY_PASSWORD, null)
    }

    actual fun isBiometricEnabled(context: Any): Boolean {
        val androidContext = context as? Context ?: return false
        val prefs = getEncryptedPrefs(androidContext)
        return prefs.getBoolean(KEY_BIOMETRIC_ENABLED, false)
    }

    actual fun clearCredentials(context: Any) {
        val androidContext = context as? Context ?: return
        val prefs = getEncryptedPrefs(androidContext)
        prefs.edit().clear().apply()
    }
}


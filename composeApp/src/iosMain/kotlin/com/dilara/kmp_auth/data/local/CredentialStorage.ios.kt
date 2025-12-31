package com.dilara.kmp_auth.data.local

actual object CredentialStorage {
    actual fun saveCredentials(context: Any, email: String, password: String) {
    }

    actual fun getEmail(context: Any): String? = null

    actual fun getPassword(context: Any): String? = null

    actual fun isBiometricEnabled(context: Any): Boolean = false

    actual fun clearCredentials(context: Any) {
    }
}


package com.dilara.kmp_auth.data.local

expect object CredentialStorage {
    fun saveCredentials(context: Any, email: String, password: String)
    fun getEmail(context: Any): String?
    fun getPassword(context: Any): String?
    fun isBiometricEnabled(context: Any): Boolean
    fun clearCredentials(context: Any)
}


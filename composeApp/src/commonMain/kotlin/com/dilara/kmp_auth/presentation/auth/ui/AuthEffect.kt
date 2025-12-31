package com.dilara.kmp_auth.presentation.auth.ui

sealed class AuthEffect {
    data class ShowError(val message: String) : AuthEffect()
    object NavigateToHome : AuthEffect()
    object NavigateToLogin : AuthEffect()
}



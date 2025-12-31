package com.dilara.kmp_auth.presentation.auth.ui

sealed class AuthEvent {
    data class SignInWithEmail(val email: String, val password: String) : AuthEvent()
    data class SignUpWithEmail(val email: String, val password: String) : AuthEvent()
    object SignInWithGoogle : AuthEvent()
    object SignInWithApple : AuthEvent()
    object SignOut : AuthEvent()
    object ClearError : AuthEvent()
    object ToggleAuthMode : AuthEvent()
    object TogglePasswordVisibility : AuthEvent()
    object ShowSocialSheet : AuthEvent()
    object HideSocialSheet : AuthEvent()
}


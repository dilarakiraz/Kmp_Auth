package com.dilara.kmp_auth.presentation.auth.ui

import com.dilara.kmp_auth.domain.entity.User

data class AuthState(
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val errorMessage: String? = null,
    val isSignUpMode: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val showSocialSheet: Boolean = false
)


package com.dilara.kmp_auth.presentation.auth.ui

import androidx.compose.runtime.Composable

@Composable
expect fun HandleGoogleSignIn(
    shouldLaunch: Boolean,
    onResult: (com.dilara.kmp_auth.domain.entity.AuthResult) -> Unit
)


package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable

expect class BiometricHelper {
    fun isBiometricAvailable(): Boolean
    fun authenticate(
        title: String,
        subtitle: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}

@Composable
expect fun rememberBiometricHelper(): BiometricHelper


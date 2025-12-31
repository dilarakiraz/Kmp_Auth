package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable

actual class BiometricHelper {
    actual fun isBiometricAvailable(): Boolean {
        return false
    }

    actual fun authenticate(
        title: String,
        subtitle: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        onError("iOS biyometrik kimlik doğrulama henüz implement edilmedi")
    }
}

@Composable
actual fun rememberBiometricHelper(): BiometricHelper {
    return BiometricHelper()
}


package com.dilara.kmp_auth.presentation.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.dilara.kmp_auth.domain.entity.AuthResult

@Composable
actual fun HandleGoogleSignIn(
    shouldLaunch: Boolean,
    onResult: (AuthResult) -> Unit
) {
    LaunchedEffect(shouldLaunch) {
        if (shouldLaunch) {
            // iOS Google Sign-In şimdilik desteklenmiyor
            // Sadece Android'de çalışmaktadır
            onResult(
                AuthResult.Error(
                    "iOS'ta Google Sign-In şu anda desteklenmiyor. " +
                    "Sadece Android platformunda çalışmaktadır."
                )
            )
        }
    }
}


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
            AuthResult.Error(
                "iOS'ta Google Sign-In henüz tam olarak uygulanmadı. " +
                        "iOS için Google Sign-In eklemek için Firebase iOS SDK ve Google Sign-In SDK'nın " +
                        "native interop ile entegre edilmesi gerekiyor. " +
                        "Şimdilik Android'de çalışmaktadır."
            )
        }
    }
}


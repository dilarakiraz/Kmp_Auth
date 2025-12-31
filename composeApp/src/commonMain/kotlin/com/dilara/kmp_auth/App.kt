package com.dilara.kmp_auth

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import com.dilara.kmp_auth.presentation.common.AppContainer
import com.dilara.kmp_auth.presentation.navigation.NavGraph

@Composable
fun App() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        NavGraph(
            authViewModel = AppContainer.authViewModel
        )
    }
}
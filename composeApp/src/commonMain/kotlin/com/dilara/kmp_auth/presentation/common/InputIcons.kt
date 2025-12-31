package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

expect object InputIcons {
    @Composable
    fun Email(): ImageVector
    @Composable
    fun Password(): ImageVector
}


package com.dilara.kmp_auth.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

actual object InputIcons {
    @Composable
    actual fun Email(): ImageVector = Icons.Default.Email
    
    @Composable
    actual fun Password(): ImageVector = Icons.Default.Lock
}


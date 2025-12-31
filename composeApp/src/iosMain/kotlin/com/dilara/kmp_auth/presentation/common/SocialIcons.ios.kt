package com.dilara.kmp_auth.presentation.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter

actual object SocialIcons {
    @Composable
    actual fun Google(): Painter = rememberVectorPainter(Icons.Default.AccountCircle)
    
    @Composable
    actual fun Apple(): Painter = rememberVectorPainter(Icons.Default.Phone)
}


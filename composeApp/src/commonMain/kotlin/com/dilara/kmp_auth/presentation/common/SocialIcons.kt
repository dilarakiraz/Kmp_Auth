package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

expect object SocialIcons {
    @Composable
    fun Google(): Painter
    @Composable
    fun Apple(): Painter
}


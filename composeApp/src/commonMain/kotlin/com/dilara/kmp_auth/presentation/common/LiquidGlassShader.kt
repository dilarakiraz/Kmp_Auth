package com.dilara.kmp_auth.presentation.common

import androidx.compose.ui.Modifier

/**
 * Expect declaration for platform-specific liquid glass shader implementation
 */
expect fun Modifier.liquidGlassShader(
    time: Float,
    intensity: Float = 1f
): Modifier


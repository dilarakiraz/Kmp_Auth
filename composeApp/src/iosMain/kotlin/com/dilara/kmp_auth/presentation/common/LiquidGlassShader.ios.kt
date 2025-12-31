package com.dilara.kmp_auth.presentation.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.cos
import kotlin.math.sin

/**
 * iOS fallback implementation using Compose drawing API
 * Creates a similar liquid glass effect without AGSL shaders
 */
actual fun Modifier.liquidGlassShader(
    time: Float,
    intensity: Float
): Modifier {
    return this.drawWithCache {
        onDrawWithContent {
            val centerX = size.width / 2
            val centerY = size.height / 2
            
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f * intensity),
                        Color.White.copy(alpha = 0.05f * intensity),
                        Color.White.copy(alpha = 0.1f * intensity),
                        Color.White.copy(alpha = 0.08f)
                    ),
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    radius = size.maxDimension
                )
            )
            
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.White.copy(alpha = 0.08f * intensity),
                        Color.Transparent
                    ),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(size.width, size.height)
                )
            )
            
            drawContent()
        }
    }
}


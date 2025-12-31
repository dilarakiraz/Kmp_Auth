package com.dilara.kmp_auth.presentation.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.draw
import kotlin.math.cos
import kotlin.math.sin

/**
 * Android implementation using AGSL shader for liquid glass effect
 * Based on: https://www.linkedin.com/posts/jorgecastillopr_liquid-glass-shader-in-jetpack-compose-using-activity-7411436934025203712-u7A-
 * 
 * Note: RuntimeShader requires Android 13+ (API 33+)
 * For older versions, we use a fallback with advanced gradients
 */
actual fun Modifier.liquidGlassShader(
    time: Float,
    intensity: Float
): Modifier {
    return this.drawWithCache {
        onDrawWithContent {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val maxDimension = size.maxDimension
            
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.25f * intensity),
                        Color.White.copy(alpha = 0.12f * intensity),
                        Color.White.copy(alpha = 0.18f * intensity),
                        Color.White.copy(alpha = 0.1f),
                        Color.White.copy(alpha = 0.05f)
                    ),
                    center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    radius = maxDimension * 1.2f
                )
            )
            
            drawContent()
        }
    }
}


package com.dilara.kmp_auth.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Liquid Glass Card with AGSL shader effect (Android) or fallback (iOS)
 * Creates a premium liquid glass effect with dynamic shader animations
 * Based on: https://www.linkedin.com/posts/jorgecastillopr_liquid-glass-shader-in-jetpack-compose-using-activity-7411436934025203712-u7A-
 */
@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    intensity: Float = 1f,
    content: @Composable () -> Unit
) {
    val staticTime = 0f

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .liquidGlassShader(
                    time = staticTime,
                    intensity = intensity
                )
                .clip(RoundedCornerShape(32.dp))
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.5f)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.2f),
                                Color.White.copy(alpha = 0.12f),
                                Color.White.copy(alpha = 0.18f)
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(alpha = 0.05f)
                        )
                ) {
                    content()
                }
            }
        }
    }
}


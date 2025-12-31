package com.dilara.kmp_auth.presentation.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object GlassButtonStyles {
    val primaryShape = RoundedCornerShape(24.dp)
    val secondaryShape = RoundedCornerShape(20.dp)
    val fabContainerColor = Color.White.copy(alpha = 0.2f)
    val fabShape = CircleShape
}

@Composable
fun primaryButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = Color.White.copy(alpha = 0.15f),
    disabledContainerColor = Color.White.copy(alpha = 0.05f)
)

@Composable
fun secondaryButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
    containerColor = Color.White.copy(alpha = 0.2f),
    disabledContainerColor = Color.White.copy(alpha = 0.1f)
)

@Composable
fun primaryButtonElevation() = ButtonDefaults.buttonElevation(
    defaultElevation = 0.dp,
    pressedElevation = 4.dp
)

@Composable
fun fabButtonElevation() = FloatingActionButtonDefaults.elevation(
    defaultElevation = 0.dp,
    pressedElevation = 2.dp
)

@Composable
fun Modifier.glassButtonAlpha(enabled: Boolean): Modifier {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.5f,
        animationSpec = tween(durationMillis = 200),
        label = "button_alpha"
    )
    return this.alpha(alpha)
}


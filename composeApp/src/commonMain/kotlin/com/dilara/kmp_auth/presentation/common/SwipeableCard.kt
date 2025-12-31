package com.dilara.kmp_auth.presentation.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun SwipeableCard(
    onSwipe: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var dragOffset by remember { mutableStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val swipeThreshold = with(density) { 100.dp.toPx() }
    
    val rotationY by animateFloatAsState(
        targetValue = if (isDragging) {
            (dragOffset / swipeThreshold) * 90f
        } else {
            0f
        },
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "card_rotation"
    )
    
    val cardAlpha by animateFloatAsState(
        targetValue = if (isDragging) {
            1f - (abs(dragOffset) / swipeThreshold) * 0.3f
        } else {
            1f
        },
        animationSpec = tween(200),
        label = "card_alpha"
    )

    LiquidGlassCard(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .graphicsLayer {
                this.rotationY = rotationY
                this.alpha = cardAlpha
                cameraDistance = 8f * density.density
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        if (abs(dragOffset) > swipeThreshold) {
                            onSwipe()
                        }
                        dragOffset = 0f
                        isDragging = false
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        dragOffset += dragAmount
                        dragOffset = dragOffset.coerceIn(-swipeThreshold * 1.5f, swipeThreshold * 1.5f)
                    }
                )
            },
        intensity = 1.2f
    ) {
        content()
    }
}


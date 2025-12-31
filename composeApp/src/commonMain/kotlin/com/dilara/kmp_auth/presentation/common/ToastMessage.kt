package com.dilara.kmp_auth.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

@Composable
fun ToastMessage(
    message: String?,
    isError: Boolean = true,
    autoHideDuration: Long = 4000,
    onHide: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(message) {
        if (message != null) {
            visible = true
            delay(autoHideDuration)
            visible = false
            onHide?.invoke()
        } else {
            visible = false
        }
    }
    AnimatedVisibility(
        visible = visible && message != null,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ) + fadeIn(
            animationSpec = tween(300)
        ) + scaleIn(
            initialScale = 0.8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        ) + scaleOut(
            targetScale = 0.8f,
            animationSpec = tween(300)
        ),
        modifier = modifier
            .zIndex(2000f)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isError) {
                            listOf(
                                Color(0xFFE53935).copy(alpha = 0.95f),
                                Color(0xFFC62828).copy(alpha = 0.95f)
                            )
                        } else {
                            listOf(
                                Color(0xFF4CAF50).copy(alpha = 0.95f),
                                Color(0xFF388E3C).copy(alpha = 0.95f)
                            )
                        }
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = message ?: "",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}


package com.dilara.kmp_auth.presentation.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

object CardAnimations {
    val entrance = fadeIn(animationSpec = tween(800)) +
            slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(800, easing = FastOutSlowInEasing)
            )
    
    val exit = fadeOut() + slideOutVertically()
}

object ContentAnimations {
    val fadeSlideUp = fadeIn(animationSpec = tween(300)) +
            slideInVertically(
                initialOffsetY = { -it / 2 },
                animationSpec = tween(300)
            ) togetherWith
            fadeOut(animationSpec = tween(300)) +
            slideOutVertically(
                targetOffsetY = { it / 2 },
                animationSpec = tween(300)
            )
    
    val fadeSlideDown = fadeIn(animationSpec = tween(300)) +
            slideInVertically(
                initialOffsetY = { it / 4 },
                animationSpec = tween(300)
            ) togetherWith
            fadeOut(animationSpec = tween(300)) +
            slideOutVertically(
                targetOffsetY = { -it / 4 },
                animationSpec = tween(300)
            )
}


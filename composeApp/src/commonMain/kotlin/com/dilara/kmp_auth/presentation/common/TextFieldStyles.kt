package com.dilara.kmp_auth.presentation.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object GlassTextFieldStyles {
    val shape = RoundedCornerShape(16.dp)
    val textColor = Color.White.copy(alpha = 0.7f)
}

@Composable
fun glassTextFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedContainerColor = Color.White.copy(alpha = 0.1f),
    unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
    focusedIndicatorColor = Color.White.copy(alpha = 0.6f),
    unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
    focusedLabelColor = Color.White.copy(alpha = 0.9f),
    unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
)


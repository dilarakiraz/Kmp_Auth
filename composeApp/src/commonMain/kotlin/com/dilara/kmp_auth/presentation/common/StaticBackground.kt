package com.dilara.kmp_auth.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Static modern background pattern
 * Clean, modern pattern without distracting animations
 * Perfect for iOS and makes glass effects visible
 */
@Composable
fun StaticModernBackground(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0F0C29),
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    ),
                    center = Offset(0f, 0f),
                    radius = 2000f
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawStaticDotsPattern(size = size)
            drawStaticGridPattern(size = size)
        }
    }
}

/**
 * Static dots pattern - clean and modern
 */
private fun DrawScope.drawStaticDotsPattern(size: androidx.compose.ui.geometry.Size) {
    val dotSize = 3f
    val spacing = 50f
    val dotColor = Color(0xFF3B82F6).copy(alpha = 0.25f) // Increased from 0.12f
    val dotColor2 = Color(0xFF6366F1).copy(alpha = 0.22f) // Increased from 0.1f
    val dotColor3 = Color(0xFF06B6D4).copy(alpha = 0.2f) // Added third color for variety
    
    var y = 0f
    var row = 0
    while (y < size.height + spacing) {
        val offsetX = if (row % 2 == 0) 0f else spacing / 2
        var x = offsetX
        
        while (x < size.width + spacing) {
            val colorIndex = ((x + y).toInt() / spacing.toInt()) % 3
            val color = when (colorIndex) {
                0 -> dotColor
                1 -> dotColor2
                else -> dotColor3
            }
            
            drawCircle(
                color = color,
                radius = dotSize,
                center = Offset(x, y)
            )
            x += spacing
        }
        y += spacing * 0.866f
        row++
    }
}

/**
 * Static grid pattern - subtle texture
 */
private fun DrawScope.drawStaticGridPattern(size: androidx.compose.ui.geometry.Size) {
    val gridSize = 100f
    val gridColor = Color(0xFF1E3A8A).copy(alpha = 0.12f) // Increased from 0.06f
    
    var x = 0f
    while (x < size.width) {
        drawLine(
            color = gridColor,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = 1f // Increased from 0.5f
        )
        x += gridSize
    }
    
    var y = 0f
    while (y < size.height) {
        drawLine(
            color = gridColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1f // Increased from 0.5f
        )
        y += gridSize
    }
    
    val diagonalColor = Color(0xFF3B82F6).copy(alpha = 0.15f) // Increased from 0.05f
    val diagonalSpacing = 120f
    
    var diagonalX = -size.height
    while (diagonalX < size.width + size.height) {
        drawLine(
            color = diagonalColor,
            start = Offset(diagonalX, 0f),
            end = Offset(diagonalX + size.height, size.height),
            strokeWidth = 1.5f // Increased from 1f
        )
        diagonalX += diagonalSpacing
    }
    
    // Additional subtle diagonal lines in opposite direction
    val diagonalColor2 = Color(0xFF6366F1).copy(alpha = 0.1f)
    var diagonalX2 = size.width + size.height
    while (diagonalX2 > -size.height) {
        drawLine(
            color = diagonalColor2,
            start = Offset(diagonalX2, 0f),
            end = Offset(diagonalX2 - size.height, size.height),
            strokeWidth = 1f
        )
        diagonalX2 -= diagonalSpacing
    }
}


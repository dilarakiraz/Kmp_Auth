package com.dilara.kmp_auth.presentation.home.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
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
import androidx.compose.ui.unit.dp
import com.dilara.kmp_auth.domain.entity.User
import com.dilara.kmp_auth.presentation.auth.ui.GlassmorphicButton
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(
    user: User,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    var cardVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        cardVisible = true
    }

    HomeScreenContent(
        user = user,
        onSignOut = onSignOut,
        cardVisible = cardVisible,
        modifier = modifier
    )
}

@Composable
private fun HomeScreenContent(
    user: User,
    onSignOut: () -> Unit,
    cardVisible: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        com.dilara.kmp_auth.presentation.common.StaticModernBackground()
        
        // Content overlay
        AnimatedVisibility(
            visible = cardVisible,
            enter = fadeIn(animationSpec = tween(800)) +
                    slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(800, easing = androidx.compose.animation.core.FastOutSlowInEasing)
                    ) + scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(800, easing = androidx.compose.animation.core.FastOutSlowInEasing)
            ),
            exit = fadeOut() + slideOutVertically() + scaleOut()
        ) {
            com.dilara.kmp_auth.presentation.common.LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = if (user.displayName != null) {
                            "Welcome, ${user.displayName}!"
                        } else {
                            user.email?.let { "Welcome!" } ?: "Welcome!"
                        },
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    user.email?.let { email ->
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    GlassmorphicButton(
                        text = "Sign Out",
                        onClick = onSignOut,
                        enabled = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        HomeScreenContent(
            user = User(
                id = "12345678",
                email = "dilarakiraz@gmail.com",
                displayName = "Dilara Kiraz",
                photoUrl = null
            ),
            onSignOut = { },
            cardVisible = true,
            modifier = Modifier.fillMaxSize()
        )
    }
}



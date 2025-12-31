package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.dilara.kmp_auth.R

actual object SocialIcons {
    @Composable
    actual fun Google(): Painter {
        return painterResource(R.drawable.ic_google)
    }
    
    @Composable
    actual fun Apple(): Painter {
        return painterResource(R.drawable.ic_apple)
    }
}


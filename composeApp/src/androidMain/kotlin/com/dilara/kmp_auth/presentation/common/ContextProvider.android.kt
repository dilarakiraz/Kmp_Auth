package com.dilara.kmp_auth.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getContext(): Any? = LocalContext.current


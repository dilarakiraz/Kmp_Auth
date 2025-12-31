package com.dilara.kmp_auth.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dilara.kmp_auth.domain.entity.User
import com.dilara.kmp_auth.presentation.auth.ui.AuthEffect
import com.dilara.kmp_auth.presentation.auth.ui.AuthEvent
import com.dilara.kmp_auth.presentation.auth.ui.AuthScreen
import com.dilara.kmp_auth.presentation.auth.viewmodel.AuthViewModel
import com.dilara.kmp_auth.presentation.home.ui.HomeScreen

sealed class Screen {
    object Auth : Screen()
    data class Home(val user: User) : Screen()
}

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val state by authViewModel.state.collectAsState()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Auth) }

    LaunchedEffect(state.currentUser) {
        when {
            state.currentUser != null -> {
                currentScreen = Screen.Home(state.currentUser!!)
            }
            state.currentUser == null -> {
                currentScreen = Screen.Auth
            }
        }
    }

    LaunchedEffect(Unit) {
        authViewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.NavigateToHome -> {
                    state.currentUser?.let {
                        currentScreen = Screen.Home(it)
                    }
                }
                is AuthEffect.NavigateToLogin -> {
                    currentScreen = Screen.Auth
                }
                is AuthEffect.ShowError -> {
                }
            }
        }
    }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            if (targetState is Screen.Home && initialState is Screen.Auth) {
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(600, easing = androidx.compose.animation.core.FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(600)) togetherWith
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(600, easing = androidx.compose.animation.core.FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(600))
            } else {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(600, easing = androidx.compose.animation.core.FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(600)) togetherWith
                slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(600, easing = androidx.compose.animation.core.FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(600))
            }
        },
        label = "screen_transition",
        modifier = modifier.fillMaxSize()
    ) { screen ->
        when (screen) {
            is Screen.Auth -> {
                AuthScreen(
                    viewModel = authViewModel,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is Screen.Home -> {
                HomeScreen(
                    user = screen.user,
                    onSignOut = {
                        authViewModel.handleEvent(AuthEvent.SignOut)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


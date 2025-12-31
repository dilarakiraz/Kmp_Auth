package com.dilara.kmp_auth.presentation.auth.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.kmp_auth.presentation.auth.viewmodel.AuthViewModel
import com.dilara.kmp_auth.presentation.common.GlassSheet
import com.dilara.kmp_auth.presentation.common.LiquidGlassCard
import com.dilara.kmp_auth.presentation.common.PasswordIcons.Visibility
import com.dilara.kmp_auth.presentation.common.PasswordIcons.VisibilityOff
import com.dilara.kmp_auth.presentation.common.StaticModernBackground
import com.dilara.kmp_auth.presentation.common.rememberBiometricHelper
import com.dilara.kmp_auth.presentation.common.FingerprintIcon
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    var shouldLaunchGoogleSignIn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.ShowError -> {
                    // Error is already shown in state
                }

                is AuthEffect.NavigateToHome -> {
                    // Navigation will be handled by parent
                }

                is AuthEffect.NavigateToLogin -> {
                    // Navigation will be handled by parent
                }
            }
        }
    }
    
    HandleGoogleSignIn(
        shouldLaunch = shouldLaunchGoogleSignIn,
        onResult = { result ->
            shouldLaunchGoogleSignIn = false
            viewModel.handleGoogleSignInResult(result)
        }
    )

    AuthScreenContent(
        state = state,
        onEmailChange = { viewModel.updateEmail(it) },
        onPasswordChange = { viewModel.updatePassword(it) },
        onSignInWithEmail = { viewModel.handleEvent(AuthEvent.SignInWithEmail(state.email, state.password)) },
        onSignUpWithEmail = { viewModel.handleEvent(AuthEvent.SignUpWithEmail(state.email, state.password)) },
        onSignInWithGoogle = { 
            viewModel.handleEvent(AuthEvent.SignInWithGoogle)
            shouldLaunchGoogleSignIn = true
        },
        onSignInWithApple = { viewModel.handleEvent(AuthEvent.SignInWithApple) },
        onSignInWithBiometric = { viewModel.handleEvent(AuthEvent.SignInWithBiometric) },
        onToggleAuthMode = { viewModel.handleEvent(AuthEvent.ToggleAuthMode) },
        onTogglePasswordVisibility = { viewModel.handleEvent(AuthEvent.TogglePasswordVisibility) },
        onShowSocialSheet = { viewModel.handleEvent(AuthEvent.ShowSocialSheet) },
        onHideSocialSheet = { viewModel.handleEvent(AuthEvent.HideSocialSheet) },
        modifier = modifier
    )
}

@Composable
private fun AuthScreenContent(
    state: AuthState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInWithEmail: () -> Unit,
    onSignUpWithEmail: () -> Unit,
    onSignInWithGoogle: () -> Unit,
    onSignInWithApple: () -> Unit,
    onSignInWithBiometric: () -> Unit,
    onToggleAuthMode: () -> Unit,
    onTogglePasswordVisibility: () -> Unit,
    onShowSocialSheet: () -> Unit,
    onHideSocialSheet: () -> Unit,
    modifier: Modifier = Modifier
) {
    val biometricHelper = rememberBiometricHelper()
    val isBiometricAvailable = remember { biometricHelper.isBiometricAvailable() }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StaticModernBackground()

        var cardVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            cardVisible = true
        }

        AnimatedVisibility(
            visible = cardVisible,
            enter = fadeIn(animationSpec = tween(800)) +
                    slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(800, easing = FastOutSlowInEasing)
                    ),
            exit = fadeOut() + slideOutVertically()
        ) {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                intensity = 1.2f
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AnimatedContent(
                        targetState = state.isSignUpMode,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) +
                                    slideInVertically(
                                        initialOffsetY = { -it / 2 },
                                        animationSpec = tween(300)
                                    ) togetherWith
                                    fadeOut(animationSpec = tween(300)) +
                                    slideOutVertically(
                                        targetOffsetY = { it / 2 },
                                        animationSpec = tween(300)
                                    )
                        },
                        label = "auth_mode_transition"
                    ) { isSignUp ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Hoş Geldiniz",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = if (isSignUp) "Hesap oluşturun" else "Devam etmek için giriş yapın",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                            )
                        }
                    }

                    AnimatedContent(
                        targetState = state.isSignUpMode,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) +
                                    slideInVertically(
                                        initialOffsetY = { it / 4 },
                                        animationSpec = tween(300)
                                    ) togetherWith
                                    fadeOut(animationSpec = tween(300)) +
                                    slideOutVertically(
                                        targetOffsetY = { -it / 4 },
                                        animationSpec = tween(300)
                                    )
                        },
                        label = "input_fields_transition"
                    ) { isSignUp ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = state.email,
                                onValueChange = onEmailChange,
                                label = { Text("E-posta", color = Color.White.copy(alpha = 0.7f)) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !state.isLoading,
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Email
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.White.copy(alpha = 0.1f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                                    focusedIndicatorColor = Color.White.copy(alpha = 0.6f),
                                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                                    focusedLabelColor = Color.White.copy(alpha = 0.9f),
                                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )

                            OutlinedTextField(
                                value = state.password,
                                onValueChange = onPasswordChange,
                                label = { Text("Şifre", color = Color.White.copy(alpha = 0.7f)) },
                                modifier = Modifier.fillMaxWidth(),
                                enabled = !state.isLoading,
                                singleLine = true,
                                visualTransformation = if (state.isPasswordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                                trailingIcon = {
                                    IconButton(onClick = onTogglePasswordVisibility) {
                                        Icon(
                                            imageVector = if (state.isPasswordVisible) {
                                                VisibilityOff
                                            } else {
                                                Visibility
                                            },
                                            contentDescription = if (state.isPasswordVisible) "Şifreyi gizle" else "Şifreyi göster",
                                            tint = Color.White.copy(alpha = 0.7f)
                                        )
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedContainerColor = Color.White.copy(alpha = 0.1f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                                    focusedIndicatorColor = Color.White.copy(alpha = 0.6f),
                                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                                    focusedLabelColor = Color.White.copy(alpha = 0.9f),
                                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )

                            GlassmorphicButton(
                                text = if (isSignUp) "Kayıt Ol" else "Giriş Yap",
                                onClick = if (isSignUp) onSignUpWithEmail else onSignInWithEmail,
                                enabled = !state.isLoading && state.email.isNotBlank() && state.password.isNotBlank(),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    if (!state.isSignUpMode && isBiometricAvailable) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(1.dp)
                                    .background(Color.White.copy(alpha = 0.3f))
                            )
                            
                            Text(
                                text = "VEYA",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                            
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(1.dp)
                                    .background(Color.White.copy(alpha = 0.3f))
                            )
                        }
                        
                        BiometricButton(
                            onClick = {
                                biometricHelper.authenticate(
                                    title = "Parmak İzi ile Giriş",
                                    subtitle = "Parmak izinizi kullanarak giriş yapın",
                                    onSuccess = {
                                        onSignInWithBiometric()
                                    },
                                    onError = { error ->
                                    }
                                )
                            },
                            enabled = !state.isLoading,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    TextButton(
                        onClick = onToggleAuthMode,
                        enabled = !state.isLoading
                    ) {
                        Text(
                            text = if (state.isSignUpMode) "Zaten hesabınız var mı? Giriş Yap" else "Hesabınız yok mu? Kayıt Ol",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }


                    state.errorMessage?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }

        FloatingSocialIcons(
            onShowSocialSheet = onShowSocialSheet,
            enabled = !state.isLoading
        )
        
        if (!state.isSignUpMode && isBiometricAvailable) {
            BiometricFloatingButton(
                onClick = {
                    biometricHelper.authenticate(
                        title = "Parmak İzi ile Giriş",
                        subtitle = "Parmak izinizi kullanarak giriş yapın",
                        onSuccess = {
                            onSignInWithBiometric()
                        },
                        onError = { error ->
                        }
                    )
                },
                enabled = !state.isLoading
            )
        }

        GlassSheet(
            visible = state.showSocialSheet,
            onDismiss = onHideSocialSheet
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Sosyal Giriş",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                SocialLoginButton(
                    text = "Google ile Devam Et",
                    onClick = {
                        onSignInWithGoogle()
                        onHideSocialSheet()
                    },
                    icon = "G",
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                SocialLoginButton(
                    text = "Apple ile Devam Et",
                    onClick = {
                        onSignInWithApple()
                        onHideSocialSheet()
                    },
                    icon = "A",
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun FloatingSocialIcons(
    onShowSocialSheet: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onShowSocialSheet,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp),
            containerColor = Color.White.copy(alpha = 0.2f),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Login,
                contentDescription = "Sosyal Giriş",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun SocialLoginButton(
    text: String,
    onClick: () -> Unit,
    icon: String,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.2f),
            disabledContainerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
private fun BiometricButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.5f,
        animationSpec = tween(durationMillis = 200),
        label = "biometric_button_alpha"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp)
            .alpha(alpha),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.2f),
            disabledContainerColor = Color.White.copy(alpha = 0.1f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = FingerprintIcon.Default,
                contentDescription = "Parmak İzi ile Giriş",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "Parmak İzi ile Giriş",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
private fun BiometricFloatingButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color.White.copy(alpha = 0.2f),
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(
                imageVector = FingerprintIcon.Default,
                contentDescription = "Parmak İzi ile Giriş",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun GlassmorphicButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.5f,
        animationSpec = tween(durationMillis = 200),
        label = "button_alpha"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(50.dp)
            .alpha(alpha),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.15f),
            disabledContainerColor = Color.White.copy(alpha = 0.05f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 16.sp
            )
        )
    }
}

@Preview
@Composable
private fun AuthScreenPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        AuthScreenContent(
            state = AuthState(
                isLoading = false,
                currentUser = null,
                errorMessage = null,
                isSignUpMode = false,
                email = "",
                password = ""
            ),
            onEmailChange = { },
            onPasswordChange = { },
            onSignInWithEmail = { },
            onSignUpWithEmail = { },
            onSignInWithGoogle = { },
            onSignInWithApple = { },
            onSignInWithBiometric = { },
            onToggleAuthMode = { },
            onTogglePasswordVisibility = { },
            onShowSocialSheet = { },
            onHideSocialSheet = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun AuthScreenLoadingPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        AuthScreenContent(
            state = AuthState(
                isLoading = true,
                currentUser = null,
                errorMessage = null,
                isSignUpMode = false,
                email = "user@example.com",
                password = "password"
            ),
            onEmailChange = { },
            onPasswordChange = { },
            onSignInWithEmail = { },
            onSignUpWithEmail = { },
            onSignInWithGoogle = { },
            onSignInWithApple = { },
            onSignInWithBiometric = { },
            onToggleAuthMode = { },
            onTogglePasswordVisibility = { },
            onShowSocialSheet = { },
            onHideSocialSheet = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun AuthScreenErrorPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        AuthScreenContent(
            state = AuthState(
                isLoading = false,
                currentUser = null,
                errorMessage = "Giriş başarısız. Lütfen tekrar deneyin.",
                isSignUpMode = false,
                email = "user@example.com",
                password = ""
            ),
            onEmailChange = { },
            onPasswordChange = { },
            onSignInWithEmail = { },
            onSignUpWithEmail = { },
            onSignInWithGoogle = { },
            onSignInWithApple = { },
            onSignInWithBiometric = { },
            onToggleAuthMode = { },
            onTogglePasswordVisibility = { },
            onShowSocialSheet = { },
            onHideSocialSheet = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun AuthScreenSignUpPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        AuthScreenContent(
            state = AuthState(
                isLoading = false,
                currentUser = null,
                errorMessage = null,
                isSignUpMode = true,
                email = "",
                password = ""
            ),
            onEmailChange = { },
            onPasswordChange = { },
            onSignInWithEmail = { },
            onSignUpWithEmail = { },
            onSignInWithGoogle = { },
            onSignInWithApple = { },
            onSignInWithBiometric = { },
            onToggleAuthMode = { },
            onTogglePasswordVisibility = { },
            onShowSocialSheet = { },
            onHideSocialSheet = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun GlassmorphicButtonPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A2E),
                            Color(0xFF16213E),
                            Color(0xFF0F3460)
                        )
                    )
                )
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GlassmorphicButton(
                    text = "Sign in with Google",
                    onClick = { },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                GlassmorphicButton(
                    text = "Sign in with Apple",
                    onClick = { },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }
        }
    }
}


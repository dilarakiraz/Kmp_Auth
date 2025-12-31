package com.dilara.kmp_auth.presentation.auth.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilara.kmp_auth.presentation.auth.viewmodel.AuthViewModel
import com.dilara.kmp_auth.presentation.common.CardAnimations
import com.dilara.kmp_auth.presentation.common.ContentAnimations
import com.dilara.kmp_auth.presentation.common.FingerprintIcon
import com.dilara.kmp_auth.presentation.common.GlassButtonStyles
import com.dilara.kmp_auth.presentation.common.GlassSheet
import com.dilara.kmp_auth.presentation.common.GlassTextField
import com.dilara.kmp_auth.presentation.common.GlassTextFieldStyles
import com.dilara.kmp_auth.presentation.common.InputIcons
import com.dilara.kmp_auth.presentation.common.PasswordIcons.Visibility
import com.dilara.kmp_auth.presentation.common.PasswordIcons.VisibilityOff
import com.dilara.kmp_auth.presentation.common.SocialIcons
import com.dilara.kmp_auth.presentation.common.StaticModernBackground
import com.dilara.kmp_auth.presentation.common.SwipeableCard
import com.dilara.kmp_auth.presentation.common.ToastMessage
import com.dilara.kmp_auth.presentation.common.fabButtonElevation
import com.dilara.kmp_auth.presentation.common.getContext
import com.dilara.kmp_auth.presentation.common.glassButtonAlpha
import com.dilara.kmp_auth.presentation.common.isIOS
import com.dilara.kmp_auth.presentation.common.primaryButtonColors
import com.dilara.kmp_auth.presentation.common.primaryButtonElevation
import com.dilara.kmp_auth.presentation.common.rememberBiometricHelper
import com.dilara.kmp_auth.presentation.common.secondaryButtonColors
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val context = getContext()
    val state by viewModel.state.collectAsState()
    var shouldLaunchGoogleSignIn by remember { mutableStateOf(false) }

    LaunchedEffect(context) {
        context?.let {
            viewModel.setContext(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffect.ShowError -> {}
                is AuthEffect.NavigateToHome -> {}
                is AuthEffect.NavigateToLogin -> {}
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
        onToggleRememberMe = { viewModel.handleEvent(AuthEvent.ToggleRememberMe) },
        onShowSocialSheet = { viewModel.handleEvent(AuthEvent.ShowSocialSheet) },
        onHideSocialSheet = { viewModel.handleEvent(AuthEvent.HideSocialSheet) },
        onShowForgotPassword = { viewModel.handleEvent(AuthEvent.ShowForgotPassword) },
        onHideForgotPassword = { viewModel.handleEvent(AuthEvent.HideForgotPassword) },
        onSendPasswordReset = { email -> viewModel.handleEvent(AuthEvent.SendPasswordReset(email)) },
        onSendEmailVerification = { viewModel.handleEvent(AuthEvent.SendEmailVerification) },
        onClearError = { viewModel.handleEvent(AuthEvent.ClearError) },
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
    onToggleRememberMe: () -> Unit,
    onShowSocialSheet: () -> Unit,
    onHideSocialSheet: () -> Unit,
    onShowForgotPassword: () -> Unit,
    onHideForgotPassword: () -> Unit,
    onSendPasswordReset: (String) -> Unit,
    onSendEmailVerification: () -> Unit,
    onClearError: () -> Unit,
    modifier: Modifier = Modifier
) {
    val biometricHelper = rememberBiometricHelper()
    var isBiometricAvailable by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, state.isSignUpMode) {
        if (!state.isSignUpMode) {
            isBiometricAvailable = biometricHelper.isBiometricAvailable()
        } else {
            isBiometricAvailable = false
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        StaticModernBackground()

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
        ) {
            state.errorMessage?.let { message ->
                val isError = !message.contains("gönderildi", ignoreCase = true) &&
                        !message.contains("başarılı", ignoreCase = true)
                ToastMessage(
                    message = message,
                    isError = isError,
                    onHide = onClearError,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            }
        }

        var cardVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            cardVisible = true
        }

        AnimatedVisibility(
            visible = cardVisible,
            enter = CardAnimations.entrance,
            exit = CardAnimations.exit
        ) {
            SwipeableCard(
                onSwipe = onToggleAuthMode,
                modifier = Modifier
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
                        transitionSpec = { ContentAnimations.fadeSlideUp },
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
                        transitionSpec = { ContentAnimations.fadeSlideDown },
                        label = "input_fields_transition"
                    ) { isSignUp ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            GlassTextField(
                                value = state.email,
                                onValueChange = onEmailChange,
                                label = "E-posta",
                                enabled = !state.isLoading,
                                leadingIcon = { InputIcons.Email() }
                            )

                            GlassTextField(
                                value = state.password,
                                onValueChange = onPasswordChange,
                                label = "Şifre",
                                enabled = !state.isLoading,
                                leadingIcon = { InputIcons.Password() },
                                isPassword = true,
                                isPasswordVisible = state.isPasswordVisible,
                                trailingIcon = {
                                    IconButton(onClick = onTogglePasswordVisibility) {
                                        Icon(
                                            imageVector = if (state.isPasswordVisible) VisibilityOff else Visibility,
                                            contentDescription = if (state.isPasswordVisible) "Şifreyi gizle" else "Şifreyi göster",
                                            tint = GlassTextFieldStyles.textColor
                                        )
                                    }
                                }
                            )

                            if (!isSignUp) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.pointerInput(Unit) {
                                            detectTapGestures {
                                                onToggleRememberMe()
                                            }
                                        }
                                    ) {
                                        Checkbox(
                                            checked = state.rememberMe,
                                            onCheckedChange = { onToggleRememberMe() },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color.White,
                                                uncheckedColor = Color.White.copy(alpha = 0.7f)
                                            )
                                        )
                                        Text(
                                            text = "Beni Hatırla",
                                            color = Color.White.copy(alpha = 0.8f),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }

                                    TextButton(
                                        onClick = onShowForgotPassword,
                                        enabled = !state.isLoading
                                    ) {
                                        Text(
                                            text = "Şifremi Unuttum",
                                            color = Color.White.copy(alpha = 0.8f),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }

                            GlassmorphicButton(
                                text = if (isSignUp) "Kayıt Ol" else "Giriş Yap",
                                onClick = if (isSignUp) onSignUpWithEmail else onSignInWithEmail,
                                enabled = !state.isLoading && state.email.isNotBlank() && state.password.isNotBlank(),
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (!state.isEmailVerified && state.currentUser != null) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "E-posta adresiniz doğrulanmamış!",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    TextButton(onClick = onSendEmailVerification) {
                                        Text(
                                            text = "Doğrulama E-postası Gönder",
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
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

                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }

        FloatingActionButtons(
            onShowSocialSheet = onShowSocialSheet,
            onBiometricClick = {
                if (!state.isSignUpMode && isBiometricAvailable) {
                    biometricHelper.authenticate(
                        title = "Parmak İzi ile Giriş",
                        subtitle = "Parmak izinizi kullanarak giriş yapın",
                        onSuccess = {
                            onSignInWithBiometric()
                        },
                        onError = { }
                    )
                }
            },
            isBiometricAvailable = !state.isSignUpMode && isBiometricAvailable,
            enabled = !state.isLoading
        )

        GlassSheet(
            visible = state.showForgotPassword,
            onDismiss = onHideForgotPassword
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Şifre Sıfırlama",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "E-posta adresinize şifre sıfırlama linki gönderilecektir.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                GlassTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    label = "E-posta",
                    enabled = !state.isLoading,
                    leadingIcon = { InputIcons.Email() },
                    modifier = Modifier.fillMaxWidth()
                )

                GlassmorphicButton(
                    text = "Gönder",
                    onClick = { onSendPasswordReset(state.email) },
                    enabled = !state.isLoading && state.email.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                )

                TextButton(onClick = onHideForgotPassword) {
                    Text(
                        text = "İptal",
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
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

                val isIOS = remember { isIOS() }
                if (isIOS) {
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
}

@Composable
private fun FloatingActionButtons(
    onShowSocialSheet: () -> Unit,
    onBiometricClick: () -> Unit,
    isBiometricAvailable: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    var socialButtonVisible by remember { mutableStateOf(false) }
    var biometricButtonVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        socialButtonVisible = true
    }

    LaunchedEffect(isBiometricAvailable) {
        if (isBiometricAvailable) {
            delay(200)
            biometricButtonVisible = true
        } else {
            biometricButtonVisible = false
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedVisibility(
                visible = socialButtonVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(400)) + scaleIn(
                    initialScale = 0.5f,
                    animationSpec = tween(400, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                PressableFloatingActionButton(
                    onClick = onShowSocialSheet,
                    containerColor = GlassButtonStyles.fabContainerColor,
                    shape = GlassButtonStyles.fabShape,
                    elevation = fabButtonElevation()
                ) {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Sosyal Giriş",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            if (isBiometricAvailable) {
                AnimatedVisibility(
                    visible = biometricButtonVisible,
                    enter = slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(400)) + scaleIn(
                        initialScale = 0.5f,
                        animationSpec = tween(400, easing = FastOutSlowInEasing)
                    ),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    PressableFloatingActionButton(
                        onClick = onBiometricClick,
                        containerColor = GlassButtonStyles.fabContainerColor,
                        shape = GlassButtonStyles.fabShape,
                        elevation = fabButtonElevation()
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
        }
    }
}

@Composable
private fun PressableFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    shape: Shape,
    elevation: androidx.compose.material3.FloatingActionButtonElevation,
    content: @Composable () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "fab_scale"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        containerColor = containerColor,
        shape = shape,
        elevation = elevation
    ) {
        content()
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
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "social_button_scale"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(56.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        shape = GlassButtonStyles.secondaryShape,
        colors = secondaryButtonColors(),
        elevation = primaryButtonElevation()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = when (icon) {
                    "G" -> SocialIcons.Google()
                    "A" -> SocialIcons.Apple()
                    else -> SocialIcons.Google()
                },
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
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
fun GlassmorphicButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "button_scale"
    )

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .height(50.dp)
            .glassButtonAlpha(enabled)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        shape = GlassButtonStyles.primaryShape,
        colors = primaryButtonColors(),
        elevation = primaryButtonElevation()
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
fun AuthScreenErrorPreview() {
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
            onToggleRememberMe = { },
            onShowSocialSheet = { },
            onHideSocialSheet = { },
            onShowForgotPassword = { },
            onHideForgotPassword = { },
            onSendPasswordReset = { },
            onSendEmailVerification = { },
            onClearError = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun AuthScreenSignUpPreview() {
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
            onToggleRememberMe = { },
            onShowSocialSheet = { },
            onHideSocialSheet = { },
            onShowForgotPassword = { },
            onHideForgotPassword = { },
            onSendPasswordReset = { },
            onSendEmailVerification = { },
            onClearError = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun GlassmorphicButtonPreview() {
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


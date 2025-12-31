package com.dilara.kmp_auth.presentation.auth.viewmodel

import com.dilara.kmp_auth.data.local.CredentialStorage
import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.usecase.GetCurrentUserUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithAppleUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithEmailUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithGoogleUseCase
import com.dilara.kmp_auth.domain.usecase.SignOutUseCase
import com.dilara.kmp_auth.domain.usecase.SignUpWithEmailUseCase
import com.dilara.kmp_auth.domain.repository.AuthRepository
import com.dilara.kmp_auth.presentation.auth.ui.AuthEffect
import com.dilara.kmp_auth.presentation.common.StringResourceId
import com.dilara.kmp_auth.presentation.common.StringResources
import com.dilara.kmp_auth.presentation.auth.ui.AuthEvent
import com.dilara.kmp_auth.presentation.auth.ui.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val signInWithAppleUseCase: SignInWithAppleUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val authRepository: AuthRepository
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    var context: Any? = null
        private set
    
    fun setContext(context: Any?) {
        this.context = context
    }
    
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AuthEffect>()
    val effect: SharedFlow<AuthEffect> = _effect.asSharedFlow()

    companion object {
        private const val INIT_DELAY_MS = 100L
    }

    init {
        viewModelScope.launch {
            kotlinx.coroutines.delay(INIT_DELAY_MS)
            observeCurrentUser()
        }
    }

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInWithEmail -> signInWithEmail(event.email, event.password)
            is AuthEvent.SignUpWithEmail -> signUpWithEmail(event.email, event.password)
            is AuthEvent.SignInWithGoogle -> {
                _state.update { it.copy(showSocialSheet = false, isLoading = true, errorMessage = null) }
            }
            is AuthEvent.SignInWithApple -> {
                _state.update { it.copy(showSocialSheet = false) }
                signInWithApple()
            }
            is AuthEvent.SignInWithBiometric -> {
                signInWithBiometric()
            }
            is AuthEvent.SignOut -> signOut()
            is AuthEvent.ClearError -> clearError()
            is AuthEvent.ToggleAuthMode -> toggleAuthMode()
            is AuthEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            is AuthEvent.ToggleRememberMe -> toggleRememberMe()
            is AuthEvent.ShowSocialSheet -> _state.update { it.copy(showSocialSheet = true) }
            is AuthEvent.HideSocialSheet -> _state.update { it.copy(showSocialSheet = false) }
            is AuthEvent.ShowForgotPassword -> _state.update { it.copy(showForgotPassword = true) }
            is AuthEvent.HideForgotPassword -> _state.update { it.copy(showForgotPassword = false) }
            is AuthEvent.SendPasswordReset -> sendPasswordReset(event.email)
            is AuthEvent.SendEmailVerification -> sendEmailVerification()
        }
    }
    
    fun handleGoogleSignInResult(result: AuthResult) {
        viewModelScope.launch {
            handleAuthResult(result)
        }
    }
    
    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }
    
    fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    private fun observeCurrentUser() {
        getCurrentUserUseCase()
            .onEach { user ->
                _state.update { it.copy(currentUser = user) }
                if (user != null) {
                    _effect.emit(AuthEffect.NavigateToHome)
                } else {
                    _effect.emit(AuthEffect.NavigateToLogin)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun signInWithGoogle() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            handleAuthResult(signInWithGoogleUseCase())
        }
    }

    private fun signInWithApple() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            handleAuthResult(signInWithAppleUseCase())
        }
    }

    private suspend fun handleAuthResult(result: AuthResult) {
        when (result) {
            is AuthResult.Success -> {
                _state.update { it.copy(isLoading = false, currentUser = result.user) }
                _effect.emit(AuthEffect.NavigateToHome)
            }
            is AuthResult.Error -> {
                handleError(result.message)
            }
        }
    }

    private suspend fun handleError(message: String) {
        _state.update { it.copy(isLoading = false, errorMessage = message) }
        _effect.emit(AuthEffect.ShowError(message))
    }

    private fun signOut() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            signOutUseCase()
                .onSuccess {
                    _state.update { it.copy(isLoading = false, currentUser = null) }
                    _effect.emit(AuthEffect.NavigateToLogin)
                }
                .onFailure { error ->
                    val errorMessage = error.message ?: StringResources.getString(context, StringResourceId.SIGN_OUT_FAILED)
                    _state.update { it.copy(isLoading = false, errorMessage = errorMessage) }
                    _effect.emit(AuthEffect.ShowError(errorMessage))
                }
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signInWithEmailUseCase(email, password)) {
                is AuthResult.Success -> {
                    val isEmailVerified = authRepository.isEmailVerified()
                    if (!isEmailVerified) {
                        _state.update { 
                            it.copy(
                                isLoading = false, 
                                isEmailVerified = false,
                                currentUser = result.user
                            ) 
                        }
                        val message = StringResources.getString(context, StringResourceId.EMAIL_NOT_VERIFIED)
                        _effect.emit(AuthEffect.ShowError(message))
                    } else {
                        if (_state.value.rememberMe) {
                            context?.let {
                                com.dilara.kmp_auth.data.local.CredentialStorage.saveCredentials(it, email, password)
                            }
                        }
                        _state.update { it.copy(isLoading = false, currentUser = result.user, email = "", password = "", isEmailVerified = true) }
                        _effect.emit(AuthEffect.NavigateToHome)
                    }
                }
                is AuthResult.Error -> {
                    handleError(result.message)
                }
            }
        }
    }

    private fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signUpWithEmailUseCase(email, password)) {
                is AuthResult.Success -> {
                    saveCredentialsIfRemembered(email, password)
                    sendEmailVerification()
                    _state.update { 
                        it.copy(
                            isLoading = false, 
                            currentUser = result.user, 
                            email = "", 
                            password = "",
                            isEmailVerified = false,
                            errorMessage = StringResources.getString(context, StringResourceId.SIGNUP_SUCCESS)
                        ) 
                    }
                }
                is AuthResult.Error -> {
                    handleError(result.message)
                }
            }
        }
    }

    private fun toggleAuthMode() {
        _state.update { it.copy(isSignUpMode = !it.isSignUpMode, errorMessage = null) }
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun clearError() {
        _state.update { it.copy(errorMessage = null) }
    }

    private fun toggleRememberMe() {
        _state.update { it.copy(rememberMe = !it.rememberMe) }
    }

    private fun saveCredentialsIfRemembered(email: String, password: String) {
        if (_state.value.rememberMe) {
            context?.let {
                CredentialStorage.saveCredentials(it, email, password)
            }
        }
    }

    private fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authRepository.sendPasswordResetEmail(email)
            result.onSuccess {
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        showForgotPassword = false,
                        errorMessage = StringResources.getString(context, StringResourceId.PASSWORD_RESET_SENT)
                    ) 
                }
            }.onFailure { error ->
                val errorMessage = error.message ?: StringResources.getString(context, StringResourceId.PASSWORD_RESET_FAILED)
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = errorMessage
                    ) 
                }
                _effect.emit(AuthEffect.ShowError(errorMessage))
            }
        }
    }

    private fun sendEmailVerification() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val result = authRepository.sendEmailVerification()
            result.onSuccess {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = StringResources.getString(context, StringResourceId.EMAIL_VERIFICATION_SENT)
                    ) 
                }
            }.onFailure { error ->
                val errorMessage = error.message ?: StringResources.getString(context, StringResourceId.EMAIL_VERIFICATION_FAILED)
                _state.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = errorMessage
                    ) 
                }
                _effect.emit(AuthEffect.ShowError(errorMessage))
            }
        }
    }

    private fun signInWithBiometric() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val currentUser = getCurrentUserUseCase().first()
            if (currentUser != null) {
                _state.update { it.copy(isLoading = false, currentUser = currentUser) }
                _effect.emit(AuthEffect.NavigateToHome)
            } else {
                if (context == null) {
                    val errorMessage = StringResources.getString(context, StringResourceId.BIOMETRIC_LOGIN_REQUIRED)
                    _state.update {
                        it.copy(
                            isLoading = false, 
                            errorMessage = errorMessage
                        ) 
                    }
                    _effect.emit(AuthEffect.ShowError(errorMessage))
                    return@launch
                }
                
                val email = com.dilara.kmp_auth.data.local.CredentialStorage.getEmail(context!!)
                val password = com.dilara.kmp_auth.data.local.CredentialStorage.getPassword(context!!)
                
                if (email != null && password != null && email.isNotBlank() && password.isNotBlank()) {
                    handleAuthResult(signInWithEmailUseCase(email, password))
                } else {
                    val errorMessage = StringResources.getString(context, StringResourceId.BIOMETRIC_LOGIN_REQUIRED)
                    _state.update {
                        it.copy(
                            isLoading = false, 
                            errorMessage = errorMessage
                        ) 
                    }
                    _effect.emit(AuthEffect.ShowError(errorMessage))
                }
            }
        }
    }
}


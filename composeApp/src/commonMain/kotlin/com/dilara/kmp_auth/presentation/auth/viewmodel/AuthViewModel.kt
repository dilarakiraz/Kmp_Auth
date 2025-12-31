package com.dilara.kmp_auth.presentation.auth.viewmodel

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.usecase.GetCurrentUserUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithAppleUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithEmailUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithGoogleUseCase
import com.dilara.kmp_auth.domain.usecase.SignOutUseCase
import com.dilara.kmp_auth.domain.usecase.SignUpWithEmailUseCase
import com.dilara.kmp_auth.presentation.auth.ui.AuthEffect
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
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AuthEffect>()
    val effect: SharedFlow<AuthEffect> = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            kotlinx.coroutines.delay(100)
            observeCurrentUser()
        }
    }

    fun handleEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInWithEmail -> signInWithEmail(event.email, event.password)
            is AuthEvent.SignUpWithEmail -> signUpWithEmail(event.email, event.password)
            is AuthEvent.SignInWithGoogle -> {
                _state.update { it.copy(showSocialSheet = false) }
                signInWithGoogle()
            }
            is AuthEvent.SignInWithApple -> {
                _state.update { it.copy(showSocialSheet = false) }
                signInWithApple()
            }
            is AuthEvent.SignOut -> signOut()
            is AuthEvent.ClearError -> clearError()
            is AuthEvent.ToggleAuthMode -> toggleAuthMode()
            is AuthEvent.TogglePasswordVisibility -> togglePasswordVisibility()
            is AuthEvent.ShowSocialSheet -> _state.update { it.copy(showSocialSheet = true) }
            is AuthEvent.HideSocialSheet -> _state.update { it.copy(showSocialSheet = false) }
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
            when (val result = signInWithGoogleUseCase()) {
                is AuthResult.Success -> {
                    _state.update { it.copy(isLoading = false, currentUser = result.user) }
                    _effect.emit(AuthEffect.NavigateToHome)
                }
                is AuthResult.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    _effect.emit(AuthEffect.ShowError(result.message))
                }
            }
        }
    }

    private fun signInWithApple() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signInWithAppleUseCase()) {
                is AuthResult.Success -> {
                    _state.update { it.copy(isLoading = false, currentUser = result.user) }
                    _effect.emit(AuthEffect.NavigateToHome)
                }
                is AuthResult.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    _effect.emit(AuthEffect.ShowError(result.message))
                }
            }
        }
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
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                    _effect.emit(AuthEffect.ShowError(error.message ?: "Sign out failed"))
                }
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signInWithEmailUseCase(email, password)) {
                is AuthResult.Success -> {
                    _state.update { it.copy(isLoading = false, currentUser = result.user, email = "", password = "") }
                    _effect.emit(AuthEffect.NavigateToHome)
                }
                is AuthResult.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    _effect.emit(AuthEffect.ShowError(result.message))
                }
            }
        }
    }

    private fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = signUpWithEmailUseCase(email, password)) {
                is AuthResult.Success -> {
                    _state.update { it.copy(isLoading = false, currentUser = result.user, email = "", password = "") }
                    _effect.emit(AuthEffect.NavigateToHome)
                }
                is AuthResult.Error -> {
                    _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                    _effect.emit(AuthEffect.ShowError(result.message))
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
}


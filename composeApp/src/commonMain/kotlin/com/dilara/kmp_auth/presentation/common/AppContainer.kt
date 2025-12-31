package com.dilara.kmp_auth.presentation.common

import com.dilara.kmp_auth.data.repository.AuthRepositoryImpl
import com.dilara.kmp_auth.domain.usecase.GetCurrentUserUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithAppleUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithEmailUseCase
import com.dilara.kmp_auth.domain.usecase.SignInWithGoogleUseCase
import com.dilara.kmp_auth.domain.usecase.SignOutUseCase
import com.dilara.kmp_auth.domain.usecase.SignUpWithEmailUseCase
import com.dilara.kmp_auth.presentation.auth.viewmodel.AuthViewModel

object AppContainer {
    private val authRepository by lazy { AuthRepositoryImpl() }
    
    val signInWithEmailUseCase by lazy { SignInWithEmailUseCase(authRepository) }
    val signUpWithEmailUseCase by lazy { SignUpWithEmailUseCase(authRepository) }
    val signInWithGoogleUseCase by lazy { SignInWithGoogleUseCase(authRepository) }
    val signInWithAppleUseCase by lazy { SignInWithAppleUseCase(authRepository) }
    val signOutUseCase by lazy { SignOutUseCase(authRepository) }
    val getCurrentUserUseCase by lazy { GetCurrentUserUseCase(authRepository) }
    
    val authViewModel by lazy {
        AuthViewModel(
            signInWithEmailUseCase = signInWithEmailUseCase,
            signUpWithEmailUseCase = signUpWithEmailUseCase,
            signInWithGoogleUseCase = signInWithGoogleUseCase,
            signInWithAppleUseCase = signInWithAppleUseCase,
            signOutUseCase = signOutUseCase,
            getCurrentUserUseCase = getCurrentUserUseCase
        )
    }
}


package com.dilara.kmp_auth.domain.usecase

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.repository.AuthRepository

class SignInWithAppleUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): AuthResult {
        return authRepository.signInWithApple()
    }
}



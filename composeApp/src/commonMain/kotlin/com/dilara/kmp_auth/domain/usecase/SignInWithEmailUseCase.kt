package com.dilara.kmp_auth.domain.usecase

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.repository.AuthRepository

class SignInWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        return authRepository.signInWithEmail(email, password)
    }
}



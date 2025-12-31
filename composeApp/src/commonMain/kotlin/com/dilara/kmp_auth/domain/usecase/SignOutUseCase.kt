package com.dilara.kmp_auth.domain.usecase

import com.dilara.kmp_auth.domain.repository.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.signOut()
    }
}



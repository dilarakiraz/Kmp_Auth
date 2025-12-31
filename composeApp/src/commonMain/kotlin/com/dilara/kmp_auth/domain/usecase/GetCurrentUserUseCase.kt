package com.dilara.kmp_auth.domain.usecase

import com.dilara.kmp_auth.domain.entity.User
import com.dilara.kmp_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return authRepository.currentUser
    }
}



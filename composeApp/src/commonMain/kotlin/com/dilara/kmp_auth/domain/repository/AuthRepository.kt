package com.dilara.kmp_auth.domain.repository

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    
    suspend fun signInWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(email: String, password: String): AuthResult
    suspend fun signInWithGoogle(): AuthResult
    suspend fun signInWithApple(): AuthResult
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): User?
}


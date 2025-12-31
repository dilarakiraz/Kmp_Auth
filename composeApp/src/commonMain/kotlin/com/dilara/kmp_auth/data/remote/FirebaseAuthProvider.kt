package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User

interface FirebaseAuthProvider {
    suspend fun signInWithEmail(email: String, password: String): AuthResult
    suspend fun signUpWithEmail(email: String, password: String): AuthResult
    suspend fun signInWithGoogle(): AuthResult
    suspend fun signInWithApple(): AuthResult
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): User?
    fun observeAuthState(): kotlinx.coroutines.flow.Flow<User?>
}

expect class FirebaseAuthProviderImpl() : FirebaseAuthProvider


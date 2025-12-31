package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User
import kotlinx.coroutines.flow.flowOf

actual class FirebaseAuthProviderImpl actual constructor() : FirebaseAuthProvider {
    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return AuthResult.Error("Email Sign-In not implemented yet")
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return AuthResult.Error("Email Sign-Up not implemented yet")
    }

    override suspend fun signInWithGoogle(): AuthResult {
        return AuthResult.Error("Google Sign-In not implemented yet")
    }

    override suspend fun signInWithApple(): AuthResult {
        return AuthResult.Error("Apple Sign-In not implemented yet")
    }

    override suspend fun signOut(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getCurrentUser(): User? {
        return null
    }

    override fun observeAuthState(): kotlinx.coroutines.flow.Flow<User?> {
        return flowOf(null)
    }
}


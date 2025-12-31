package com.dilara.kmp_auth.data.repository

import com.dilara.kmp_auth.data.remote.FirebaseAuthProvider
import com.dilara.kmp_auth.data.remote.FirebaseAuthProviderImpl
import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User
import com.dilara.kmp_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val firebaseAuthProvider: FirebaseAuthProvider = FirebaseAuthProviderImpl()
) : AuthRepository {
    
    override val currentUser: Flow<User?> = firebaseAuthProvider.observeAuthState()
    
    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return firebaseAuthProvider.signInWithEmail(email, password)
    }
    
    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return firebaseAuthProvider.signUpWithEmail(email, password)
    }
    
    override suspend fun signInWithGoogle(): AuthResult {
        return firebaseAuthProvider.signInWithGoogle()
    }
    
    override suspend fun signInWithApple(): AuthResult {
        return firebaseAuthProvider.signInWithApple()
    }
    
    override suspend fun signOut(): Result<Unit> {
        return firebaseAuthProvider.signOut()
    }
    
    override suspend fun getCurrentUser(): User? {
        return firebaseAuthProvider.getCurrentUser()
    }
}


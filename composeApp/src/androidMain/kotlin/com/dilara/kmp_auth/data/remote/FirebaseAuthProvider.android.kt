package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

actual class FirebaseAuthProviderImpl actual constructor() : FirebaseAuthProvider {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toDomainUser()
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Sign in failed")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign in failed")
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.toDomainUser()
            if (user != null) {
                AuthResult.Success(user)
            } else {
                AuthResult.Error("Sign up failed")
            }
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Sign up failed")
        }
    }

    override suspend fun signInWithGoogle(): AuthResult {
        return try {
            AuthResult.Error("Google Sign-In not implemented yet")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun signInWithApple(): AuthResult {
        return try {
            AuthResult.Error("Apple Sign-In not implemented yet")
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toDomainUser()
    }

    override fun observeAuthState(): kotlinx.coroutines.flow.Flow<User?> {
        return callbackFlow {
            try {
                // Ensure Firebase is initialized
                com.google.firebase.FirebaseApp.getInstance()
                val listener = FirebaseAuth.AuthStateListener { auth ->
                    trySend(auth.currentUser?.toDomainUser())
                }
                firebaseAuth.addAuthStateListener(listener)
                awaitClose {
                    firebaseAuth.removeAuthStateListener(listener)
                }
            } catch (e: IllegalStateException) {
                trySend(null)
                close()
            }
        }
    }

    private fun FirebaseUser.toDomainUser(): User {
        return User(
            id = uid,
            email = email,
            displayName = displayName,
            photoUrl = photoUrl?.toString()
        )
    }
}


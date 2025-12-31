package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
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
            
            try {
                GoogleSignInHelper.signOut()
            } catch (_: Exception) {
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): User? {
        return try {
            val user = firebaseAuth.currentUser
            user?.reload()?.await()
            user?.toDomainUser()
        } catch (e: Exception) {
            firebaseAuth.signOut()
            null
        }
    }

    override fun observeAuthState(): kotlinx.coroutines.flow.Flow<User?> {
        return callbackFlow {
            try {
                com.google.firebase.FirebaseApp.getInstance()
                
                val listener = FirebaseAuth.AuthStateListener { auth ->
                    auth.currentUser?.let { user ->
                        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                            try {
                                user.reload().await()
                                trySend(user.toDomainUser())
                            } catch (e: Exception) {
                                firebaseAuth.signOut()
                                trySend(null)
                            }
                        }
                    } ?: run {
                        trySend(null)
                    }
                }
                firebaseAuth.addAuthStateListener(listener)
                
                firebaseAuth.currentUser?.let { user ->
                    try {
                        user.reload().await()
                        trySend(user.toDomainUser())
                    } catch (e: Exception) {
                        firebaseAuth.signOut()
                        trySend(null)
                    }
                } ?: run {
                    trySend(null)
                }
                
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


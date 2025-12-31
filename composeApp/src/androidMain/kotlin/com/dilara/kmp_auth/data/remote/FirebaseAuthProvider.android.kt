package com.dilara.kmp_auth.data.remote

import android.content.Context
import com.dilara.kmp_auth.domain.entity.AuthResult
import com.dilara.kmp_auth.domain.entity.User
import com.dilara.kmp_auth.presentation.common.StringResourceId
import com.dilara.kmp_auth.presentation.common.StringResources
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

actual class FirebaseAuthProviderImpl : FirebaseAuthProvider {
    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    
    private fun getContext(): Context? {
        return try {
            com.google.firebase.FirebaseApp.getInstance().applicationContext as? Context
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.toDomainUser()
            if (user != null) {
                AuthResult.Success(user)
            } else {
                val message = StringResources.getString(getContext(), StringResourceId.SIGN_IN_FAILED)
                AuthResult.Error(message)
            }
        } catch (e: Exception) {
            val message = e.message ?: StringResources.getString(getContext(), StringResourceId.SIGN_IN_FAILED)
            AuthResult.Error(message)
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.toDomainUser()
            if (user != null) {
                AuthResult.Success(user)
            } else {
                val message = StringResources.getString(getContext(), StringResourceId.SIGN_UP_FAILED)
                AuthResult.Error(message)
            }
        } catch (e: Exception) {
            val message = e.message ?: StringResources.getString(getContext(), StringResourceId.SIGN_UP_FAILED)
            AuthResult.Error(message)
        }
    }

    override suspend fun signInWithGoogle(): AuthResult {
        return AuthResult.Error("Google Sign-In not implemented yet")
    }

    override suspend fun signInWithApple(): AuthResult {
        return AuthResult.Error("Apple Sign-In not implemented yet")
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

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: com.google.firebase.auth.FirebaseAuthInvalidUserException) {
            val message = StringResources.getString(getContext(), StringResourceId.USER_NOT_FOUND)
            Result.failure(Exception(message))
        } catch (e: com.google.firebase.auth.FirebaseAuthException) {
            val baseMessage = StringResources.getString(getContext(), StringResourceId.EMAIL_SEND_FAILED)
            Result.failure(Exception("$baseMessage: ${e.message}"))
        } catch (e: Exception) {
            val baseMessage = StringResources.getString(getContext(), StringResourceId.UNKNOWN_ERROR)
            Result.failure(Exception("$baseMessage: ${e.message ?: StringResources.getString(getContext(), StringResourceId.UNKNOWN_ERROR)}"))
        }
    }

    override suspend fun sendEmailVerification(): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                user.sendEmailVerification().await()
                Result.success(Unit)
            } else {
                val message = StringResources.getString(getContext(), StringResourceId.NO_USER_SIGNED_IN)
                Result.failure(Exception(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isEmailVerified(): Boolean {
        return try {
            val user = firebaseAuth.currentUser
            user?.isEmailVerified ?: false
        } catch (e: Exception) {
            false
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


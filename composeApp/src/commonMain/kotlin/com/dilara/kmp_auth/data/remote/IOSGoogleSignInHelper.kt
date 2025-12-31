package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult

expect class IOSGoogleSignInHelper {
    fun configure(clientID: String)
    suspend fun signIn(): String? // Returns ID token
    fun signOut()
    suspend fun signInWithFirebase(idToken: String): AuthResult
}


package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

// iOS Google Sign-In şimdilik desteklenmiyor
// Sadece Android'de çalışmaktadır
actual class IOSGoogleSignInHelper {
    
    actual fun configure(clientID: String) {
        // iOS Google Sign-In desteklenmiyor
    }
    
    actual suspend fun signIn(): String? = suspendCancellableCoroutine { continuation ->
        continuation.resume(null)
    }
    
    actual fun signOut() {
        // iOS Google Sign-In desteklenmiyor
    }
    
    actual suspend fun signInWithFirebase(idToken: String): AuthResult = suspendCancellableCoroutine { continuation ->
        continuation.resume(AuthResult.Error("iOS Google Sign-In desteklenmiyor. Sadece Android'de çalışmaktadır."))
    }
}


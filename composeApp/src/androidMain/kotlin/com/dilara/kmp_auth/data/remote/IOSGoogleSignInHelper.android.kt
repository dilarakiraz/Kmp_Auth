package com.dilara.kmp_auth.data.remote

import com.dilara.kmp_auth.domain.entity.AuthResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

// Android'de Google Sign-In zaten GoogleSignInHelper ile yapılıyor
// Bu class sadece expect/actual uyumluluğu için placeholder
actual class IOSGoogleSignInHelper {
    
    actual fun configure(clientID: String) {
        // Android'de GoogleSignInHelper kullanılıyor
        // Bu method kullanılmıyor
    }
    
    actual suspend fun signIn(): String? = suspendCancellableCoroutine { continuation ->
        // Android'de GoogleSignInHelper kullanılıyor
        continuation.resume(null)
    }
    
    actual fun signOut() {
        // Android'de GoogleSignInHelper kullanılıyor
        // Bu method kullanılmıyor
    }
    
    actual suspend fun signInWithFirebase(idToken: String): AuthResult = suspendCancellableCoroutine { continuation ->
        // Android'de GoogleSignInHelper kullanılıyor
        continuation.resume(AuthResult.Error("Android'de GoogleSignInHelper kullanılmalı, bu class kullanılmıyor."))
    }
}


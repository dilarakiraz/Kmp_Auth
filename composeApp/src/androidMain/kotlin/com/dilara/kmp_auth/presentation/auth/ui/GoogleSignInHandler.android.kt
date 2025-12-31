package com.dilara.kmp_auth.presentation.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.dilara.kmp_auth.data.remote.GoogleSignInHelper
import com.dilara.kmp_auth.domain.entity.AuthResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
actual fun HandleGoogleSignIn(
    shouldLaunch: Boolean,
    onResult: (AuthResult) -> Unit
) {
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val intent = result.data
        
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val account = GoogleSignInHelper.getAccountFromIntent(intent)
                
                if (account != null && account.idToken != null) {
                    try {
                        val authResult = GoogleSignInHelper.signInWithFirebase(account.idToken!!)
                        
                        val user = authResult.user?.let { firebaseUser ->
                            com.dilara.kmp_auth.domain.entity.User(
                                id = firebaseUser.uid,
                                email = firebaseUser.email,
                                displayName = firebaseUser.displayName,
                                photoUrl = firebaseUser.photoUrl?.toString()
                            )
                        }
                        if (user != null) {
                            onResult(AuthResult.Success(user))
                        } else {
                            onResult(AuthResult.Error("Giriş başarısız: Kullanıcı bilgisi alınamadı"))
                        }
                    } catch (e: Exception) {
                        onResult(AuthResult.Error(e.message ?: "Giriş başarısız"))
                    }
                } else {
                    val errorMsg = when {
                        account == null -> "Google hesabı bulunamadı. Lütfen tekrar deneyin."
                        account?.idToken == null -> "ID token bulunamadı. Firebase yapılandırmasını kontrol edin."
                        else -> "Google Sign-In başarısız oldu."
                    }
                    onResult(AuthResult.Error(errorMsg))
                }
            } catch (e: Exception) {
                onResult(AuthResult.Error("Hata: ${e.message}"))
            }
        }
    }
    
    androidx.compose.runtime.LaunchedEffect(shouldLaunch) {
        if (shouldLaunch) {
            val signInIntent = GoogleSignInHelper.getSignInIntent()
            if (signInIntent != null) {
                googleSignInLauncher.launch(signInIntent)
            } else {
                onResult(AuthResult.Error("Google Sign-In not configured. Please enable it in Firebase Console."))
            }
        }
    }
}


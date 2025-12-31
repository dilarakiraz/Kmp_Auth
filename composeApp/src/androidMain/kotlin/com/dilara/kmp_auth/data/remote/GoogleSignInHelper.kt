package com.dilara.kmp_auth.data.remote

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

object GoogleSignInHelper {
    private var googleSignInClient: GoogleSignInClient? = null

    fun initialize(context: Context) {
        val webClientId = getWebClientId(context)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    private fun getWebClientId(context: Context): String {
        return try {
            val resourceId = context.resources.getIdentifier(
                "default_web_client_id",
                "string",
                context.packageName
            )
            if (resourceId != 0) {
                context.getString(resourceId)
            } else {
                ""
            }
        } catch (_: Exception) {
            ""
        }
    }

    fun getSignInIntent() = googleSignInClient?.signInIntent

    suspend fun signInWithFirebase(idToken: String): com.google.firebase.auth.AuthResult {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return FirebaseAuth.getInstance().signInWithCredential(credential).await()
    }

    suspend fun signOut() {
        googleSignInClient?.signOut()?.await()
    }

    suspend fun getAccountFromIntent(data: android.content.Intent?): GoogleSignInAccount? {
        return try {
            if (data == null) {
                return null
            }
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.await()
        } catch (e: ApiException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}


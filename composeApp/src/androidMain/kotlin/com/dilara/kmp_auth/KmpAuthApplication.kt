package com.dilara.kmp_auth

import android.app.Application
import com.dilara.kmp_auth.data.remote.GoogleSignInHelper
import com.google.firebase.FirebaseApp

class KmpAuthApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
        GoogleSignInHelper.initialize(this)
    }
}



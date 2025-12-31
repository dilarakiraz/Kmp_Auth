package com.dilara.kmp_auth.presentation.common

expect object StringResources {
    fun getString(context: Any?, id: StringResourceId): String
}

enum class StringResourceId {
    SIGN_OUT_FAILED,
    EMAIL_NOT_VERIFIED,
    SIGNUP_SUCCESS,
    PASSWORD_RESET_SENT,
    PASSWORD_RESET_FAILED,
    EMAIL_VERIFICATION_SENT,
    EMAIL_VERIFICATION_FAILED,
    BIOMETRIC_LOGIN_REQUIRED,
    SIGN_IN_FAILED,
    SIGN_UP_FAILED,
    USER_NOT_FOUND,
    EMAIL_SEND_FAILED,
    UNKNOWN_ERROR,
    NO_USER_SIGNED_IN
}


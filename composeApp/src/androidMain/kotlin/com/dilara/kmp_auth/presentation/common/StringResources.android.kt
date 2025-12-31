package com.dilara.kmp_auth.presentation.common

import android.content.Context
import com.dilara.kmp_auth.R

actual object StringResources {
    actual fun getString(context: Any?, id: StringResourceId): String {
        val androidContext = context as? Context ?: return getDefaultString(id)
        return when (id) {
            StringResourceId.SIGN_OUT_FAILED -> androidContext.getString(R.string.sign_out_failed)
            StringResourceId.EMAIL_NOT_VERIFIED -> androidContext.getString(R.string.email_not_verified)
            StringResourceId.SIGNUP_SUCCESS -> androidContext.getString(R.string.signup_success)
            StringResourceId.PASSWORD_RESET_SENT -> androidContext.getString(R.string.password_reset_sent)
            StringResourceId.PASSWORD_RESET_FAILED -> androidContext.getString(R.string.password_reset_failed)
            StringResourceId.EMAIL_VERIFICATION_SENT -> androidContext.getString(R.string.email_verification_sent)
            StringResourceId.EMAIL_VERIFICATION_FAILED -> androidContext.getString(R.string.email_verification_failed)
            StringResourceId.BIOMETRIC_LOGIN_REQUIRED -> androidContext.getString(R.string.biometric_login_required)
            StringResourceId.SIGN_IN_FAILED -> androidContext.getString(R.string.sign_in_failed)
            StringResourceId.SIGN_UP_FAILED -> androidContext.getString(R.string.sign_up_failed)
            StringResourceId.USER_NOT_FOUND -> androidContext.getString(R.string.user_not_found)
            StringResourceId.EMAIL_SEND_FAILED -> androidContext.getString(R.string.email_send_failed)
            StringResourceId.UNKNOWN_ERROR -> androidContext.getString(R.string.unknown_error)
            StringResourceId.NO_USER_SIGNED_IN -> androidContext.getString(R.string.no_user_signed_in)
        }
    }
    
    private fun getDefaultString(id: StringResourceId): String {
        return when (id) {
            StringResourceId.SIGN_OUT_FAILED -> "Çıkış yapılamadı"
            StringResourceId.EMAIL_NOT_VERIFIED -> "E-posta adresiniz doğrulanmamış. Lütfen e-posta kutunuzu kontrol edin."
            StringResourceId.SIGNUP_SUCCESS -> "Kayıt başarılı! E-posta adresinizi doğrulamak için gönderilen e-postayı (spam klasörü dahil) kontrol edin."
            StringResourceId.PASSWORD_RESET_SENT -> "Şifre sıfırlama e-postası gönderildi. Lütfen e-posta kutunuzu (spam klasörü dahil) kontrol edin."
            StringResourceId.PASSWORD_RESET_FAILED -> "Şifre sıfırlama e-postası gönderilemedi"
            StringResourceId.EMAIL_VERIFICATION_SENT -> "Doğrulama e-postası gönderildi. Lütfen e-posta kutunuzu (spam klasörü dahil) kontrol edin."
            StringResourceId.EMAIL_VERIFICATION_FAILED -> "Doğrulama e-postası gönderilemedi"
            StringResourceId.BIOMETRIC_LOGIN_REQUIRED -> "Önce email/şifre ile giriş yapmalısınız"
            StringResourceId.SIGN_IN_FAILED -> "Giriş yapılamadı"
            StringResourceId.SIGN_UP_FAILED -> "Kayıt olunamadı"
            StringResourceId.USER_NOT_FOUND -> "Bu e-posta adresi ile kayıtlı bir kullanıcı bulunamadı."
            StringResourceId.EMAIL_SEND_FAILED -> "E-posta gönderilemedi"
            StringResourceId.UNKNOWN_ERROR -> "Bilinmeyen hata"
            StringResourceId.NO_USER_SIGNED_IN -> "Giriş yapılmış kullanıcı yok"
        }
    }
}


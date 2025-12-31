package com.dilara.kmp_auth.presentation.common

actual object StringResources {
    actual fun getString(context: Any?, id: StringResourceId): String {
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


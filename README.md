# KMP Auth

Modern ve şık bir kimlik doğrulama uygulaması. Android ve iOS platformlarında çalışan Kotlin Multiplatform tabanlı authentication uygulaması.

## 📱 Demo Video

<div align="center">

[![Watch the video](https://img.shields.io/badge/▶️-Watch%20Demo%20Video-red?style=for-the-badge&logo=youtube)](https://youtube.com/shorts/q_u6Fr0PVb8)

[![KMP Auth Demo](https://img.youtube.com/vi/q_u6Fr0PVb8/0.jpg)](https://youtube.com/shorts/q_u6Fr0PVb8)

[🔗 Video'yu YouTube'da izleyin](https://youtube.com/shorts/q_u6Fr0PVb8)

</div>

## 🖼️ Ekran Görüntüleri

<div align="center">

<table>
  <tr>
    <td align="center">
      <img src="docs/screenshots/ios_login.png" alt="iOS Login Screen" width="280" style="border-radius: 12px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);"/>
      <br/><br/>
      <strong>IOS Giriş Ekranı</strong>
    </td>
    <td align="center">
      <img src="docs/screenshots/ios_register.png" alt="iOS Register Screen" width="280" style="border-radius: 12px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);"/>
      <br/><br/>
      <strong>IOS Kayıt Ekranı</strong>
    </td>
    <td align="center">
      <img src="docs/screenshots/social_entry.png" alt="Social Login Screen" width="280" style="border-radius: 12px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);"/>
      <br/><br/>
      <strong>IOS Sosyal Giriş</strong>
    </td>
  </tr>
</table>

</div>

## ✨ Özellikler

- 🔐 **Email/Password Authentication** - Firebase ile email ve şifre ile giriş
- 🍎 **Apple Sign In** - iOS için Apple ile giriş desteği
- 🔵 **Google Sign In** - Google hesabı ile giriş
- 🔒 **Biometric Authentication** - Face ID / Touch ID desteği
- 💾 **Secure Storage** - Güvenli yerel veri saklama
- 🎨 **Modern UI** - Glassmorphic tasarım ve animasyonlar
- 📱 **Cross-Platform** - Android ve iOS desteği

## 🛠️ Teknolojiler

- **Kotlin Multiplatform** - Cross-platform geliştirme
- **Compose Multiplatform** - Modern UI framework
- **Firebase Authentication** - Backend authentication servisi
- **Material Design 3** - Modern design system
- **Jetpack Compose** - Declarative UI

## 📋 Gereksinimler

### Android
- Android Studio Hedgehog | 2023.1.1 veya üzeri
- JDK 11 veya üzeri
- Android SDK 24+ (Android 7.0+)

### iOS
- Xcode 15.0 veya üzeri
- macOS 13.0 veya üzeri
- CocoaPods (iOS dependencies için)

## 🚀 Kurulum

### 1. Repository'yi klonlayın

```bash
git clone https://github.com/dilarakiraz/Kmp_Auth.git
cd Kmp_Auth
```

### 2. Android Setup

1. Android Studio'da projeyi açın
2. Gradle sync yapın
3. Firebase'i yapılandırın:
   - `composeApp/google-services.json` dosyasını Firebase Console'dan indirip ekleyin
4. Uygulamayı çalıştırın:
   ```bash
   ./gradlew :composeApp:assembleDebug
   ```

### 3. iOS Setup

1. Xcode'da projeyi açın:
   ```bash
   open iosApp/iosApp.xcworkspace
   ```
2. CocoaPods dependencies yükleyin (gerekirse):
   ```bash
   cd iosApp
   pod install
   ```
3. Firebase'i yapılandırın:
   - `GoogleService-Info.plist` dosyasını Firebase Console'dan indirip `iosApp/iosApp/` klasörüne ekleyin
4. Xcode'dan uygulamayı çalıştırın

## 📁 Proje Yapısı

```
Kmp_Auth/
├── composeApp/              # Compose Multiplatform modülü
│   ├── src/
│   │   ├── commonMain/      # Ortak kod (Android & iOS)
│   │   ├── androidMain/     # Android-specific kod
│   │   └── iosMain/         # iOS-specific kod
│   └── build.gradle.kts
├── iosApp/                  # iOS native app
├── docs/                    # Dokümantasyon ve görseller
│   └── screenshots/         # Ekran görüntüleri
└── README.md
```

## 🔧 Firebase Yapılandırması

### 1. Firebase Console Setup

1. [Firebase Console](https://console.firebase.google.com/)'a gidin
2. Yeni bir proje oluşturun
3. Authentication'ı etkinleştirin:
   - Email/Password provider
   - Google Sign-In provider
   - Apple Sign-In provider (iOS için)

### 2. Android Firebase Setup

1. Firebase Console'da Android app ekleyin
2. `google-services.json` dosyasını `composeApp/` klasörüne ekleyin

### 3. iOS Firebase Setup

1. Firebase Console'da iOS app ekleyin
2. `GoogleService-Info.plist` dosyasını `iosApp/iosApp/` klasörüne ekleyin

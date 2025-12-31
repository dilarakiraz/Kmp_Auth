# Firebase Kurulum Rehberi

## Adım 1: Firebase Console'da Proje Oluşturma

1. **Firebase Console'a git:**
   - https://console.firebase.google.com/ adresine git
   - Google hesabınla giriş yap

2. **Yeni Proje Oluştur:**
   - "Add project" veya "Proje ekle" butonuna tıkla
   - Proje adını gir: `Kmp_Auth` (veya istediğin isim)
   - Google Analytics'i isteğe bağlı olarak etkinleştir
   - "Create project" butonuna tıkla

## Adım 2: Android Uygulaması Ekleme

1. **Android Uygulaması Ekle:**
   - Firebase Console'da proje açıldıktan sonra
   - Android ikonuna tıkla (veya "Add app" → Android)
   
2. **Uygulama Bilgilerini Gir:**
   - **Android package name:** `com.dilara.kmp_auth`
   - **App nickname (optional):** `Kmp Auth Android`
   - **Debug signing certificate SHA-1 (optional):** Şimdilik boş bırakabilirsin
   - "Register app" butonuna tıkla

3. **google-services.json İndir:**
   - "Download google-services.json" butonuna tıkla
   - Dosyayı indir

## Adım 3: google-services.json Dosyasını Projeye Ekle

1. **Dosyayı Kopyala:**
   - İndirdiğin `google-services.json` dosyasını bul
   
2. **Projeye Ekle:**
   - Dosyayı şu konuma kopyala:
   ```
   composeApp/src/androidMain/google-services.json
   ```
   - **ÖNEMLİ:** Dosya `composeApp/src/androidMain/` klasörüne eklenmeli (app modülü)

3. **Android Studio'da Kontrol Et:**
   - Android Studio'da dosyanın doğru yerde olduğunu kontrol et
   - Gradle sync yap (File → Sync Project with Gradle Files)

## Adım 4: Firebase Authentication'ı Etkinleştir

1. **Firebase Console'da:**
   - Sol menüden "Authentication" seç
   - "Get started" butonuna tıkla

2. **Sign-in method'ları etkinleştir:**
   - **Email/Password:** Aç ve "Enable" yap
   - **Google:** Aç ve "Enable" yap (isteğe bağlı)
   - **Apple:** Aç ve "Enable" yap (isteğe bağlı, iOS için)

3. **Google Sign-In için:**
   - Google Sign-In'i etkinleştirdiysen
   - Support email seç (Google hesabın)
   - "Save" butonuna tıkla

## Adım 5: Projeyi Test Et

1. **Gradle Sync:**
   - Android Studio'da: `File` → `Sync Project with Gradle Files`

2. **Build:**
   - `Build` → `Clean Project`
   - `Build` → `Rebuild Project`

3. **Çalıştır:**
   - Uygulamayı çalıştır
   - Email/password ile kayıt ol veya giriş yap

## Önemli Notlar

- `google-services.json` dosyası **mutlaka** `composeApp/src/androidMain/` klasörüne eklenmeli
- Application ID (`com.dilara.kmp_auth`) Firebase Console'daki package name ile **tam olarak eşleşmeli**
- Firebase Console'da Authentication'ı etkinleştirmeyi unutma
- İlk test için Email/Password authentication yeterli

## Sorun Giderme

- **"Default FirebaseApp is not initialized" hatası:**
  - `google-services.json` dosyasının doğru yerde olduğundan emin ol
  - Gradle sync yap
  - Uygulamayı yeniden başlat

- **"FirebaseApp with name [DEFAULT] doesn't exist" hatası:**
  - `google-services.json` dosyasını kontrol et
  - Application ID'nin doğru olduğundan emin ol


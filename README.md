# 📱 PasskeysAuth KMP App

A **Compose Multiplatform** demo app showcasing passwordless authentication using passkeys. Works with the companion [PasskeysAuth Backend](https://github.com/ClarkStoro/PasskeysAuth-BE-Example) to demonstrate secure authentication across Android and iOS.

> **⚠️ Demo Project**: This is for educational/demo purposes. Do not use in production without proper security review.

## 🚀 Quick Start

### Prerequisites

- **Android Studio** (latest stable)
- **Xcode 15+** (for iOS development)
- **Java 17+**
- **Deployed backend** - Deploy the [backend](https://github.com/ClarkStoro/PasskeysAuth-BE-Example) first
- **Physical devices** recommended (passkeys work better on real devices)

### 1. Clone & Setup

```bash
git clone <your-app-repo-url>
cd passkeysauth-kmp-app-example
```

### 2. Configure Backend URL

Update the backend URL to point to your deployed instance:

**Edit `composeApp/src/commonMain/kotlin/data/remote/NetworkClient.kt`:**
```kotlin
companion object {
    private const val BASE_URL = "https://YOUR-BACKEND-DOMAIN.onrender.com/"  // ← Change this
}
```

### 3. Android Configuration

#### **Update Package Name**
1. **Rename package** in `composeApp/build.gradle.kts`:
```kotlin
android {
    namespace = "com.yourcompany.yourapp"  // ← Change this
    
    defaultConfig {
        applicationId = "com.yourcompany.yourapp"  // ← Change this
    }
}
```

2. **Update backend assetlinks.json** with your package name and fingerprint (see backend README)

#### **Get Your Debug Keystore Fingerprint**
```bash
# For development (debug keystore)
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android

# Look for SHA256 fingerprint and update your backend configuration
```

### 4. iOS Configuration

#### **Update Bundle Identifier**
In `iosApp/iosApp.xcodeproj`:
1. Select **iosApp** target
2. Go to **General** → **Identity**
3. Change **Bundle Identifier** to `com.yourcompany.yourapp`

#### **Add Associated Domains**
1. In Xcode, go to **Signing & Capabilities**
2. Add **Associated Domains** capability
3. Add domain: `webcredentials:YOUR-BACKEND-DOMAIN.onrender.com`

#### **Update Backend apple-app-site-association**
Update your backend's `.well-known/apple-app-site-association` with your Team ID:
```json
{
  "webcredentials": {
    "apps": ["YOUR_TEAM_ID.com.yourcompany.yourapp"]
  }
}
```

**Find Your Team ID:**
- Xcode → Preferences → Accounts → Select your account → View Details
- Or Apple Developer Console → Membership

## 🔧 Platform-Specific Setup

### Android Studio Setup

1. **Open project** in Android Studio
2. **Sync Gradle** (may take a few minutes)
3. **Select device** (physical device recommended)
4. **Run** → Select `composeApp` configuration

### Xcode Setup

1. **Build KMP Framework**:
```bash
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
```

2. **Open iOS project**:
```bash
open iosApp/iosApp.xcodeproj
```

3. **Configure signing** in Xcode:
   - Select **iosApp** target
   - **Signing & Capabilities** → Select your team
   - Ensure **Bundle Identifier** matches your configuration

4. **Run on device** (simulator has passkey limitations)

## 🏗️ Architecture

This app uses **MVI (Model-View-Intent)** architecture for predictable state management:

| Component | Purpose |
|-----------|---------|
| **AuthScreen** | Registration and login UI with passkey integration |
| **AuthViewModel** | Manages authentication state using MVI pattern |
| **HomeScreen** | Protected todos list demonstrating authenticated API calls |
| **PasskeysCredentialManager** | Platform-specific passkey handling (Android/iOS) |
| **NetworkClient** | HTTP client with JWT token management |
| **AuthRepository** | Authentication business logic and API communication |

## 🧪 Testing Your Setup

### 1. Verify Backend Connection
- App should show **"🔐 Passkeys Demo"** header
- Registration button should be enabled
- No network errors in logs

### 2. Test Registration Flow
1. Enter any **username**
2. Tap **"Register with Passkey"**
3. System should prompt for **biometric/passkey creation**
4. Success should show **"Registration successful!"** dialog

### 3. Test Login Flow
1. Enter **same username**
2. Tap **"Login with Passkey"**
3. System should prompt for **biometric authentication**
4. Success should navigate to **todos screen** with data

## 🚨 Troubleshooting

### "Registration Failed" Error
- ✅ **Backend running?** Check `https://YOUR-BACKEND.onrender.com/checkStatus`
- ✅ **Package name matches?** Verify assetlinks.json in backend
- ✅ **Correct fingerprint?** Double-check SHA256 in backend configuration
- ✅ **HTTPS backend?** Passkeys require secure connection

### "Network Error" 
- ✅ **BASE_URL correct?** Should end with `/` and use `https://`
- ✅ **CORS enabled?** Backend should allow cross-origin requests
- ✅ **Check logs** - Look for specific error messages in console

### Android Issues
- ✅ **Use physical device** - Emulator has passkey limitations
- ✅ **Google Play Services** updated on device
- ✅ **Screen lock enabled** - Required for passkeys
- ✅ **Fingerprint enrolled** - Or other biometric method

### iOS Issues  
- ✅ **Associated domains** properly configured in Xcode
- ✅ **Team ID correct** - Must match Apple Developer account
- ✅ **Bundle ID unique** - Cannot conflict with existing apps
- ✅ **Test on device** - Simulator has significant limitations
- ✅ **iOS 16+** - Earlier versions have limited passkey support

### Build Issues
- ✅ **Clean and rebuild**: `./gradlew clean build`
- ✅ **iOS framework**: Re-run `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode`
- ✅ **Xcode clean**: Product → Clean Build Folder
- ✅ **Gradle sync** in Android Studio

## 📚 Learn More

- [Passkeys Guide](https://developer.android.com/training/sign-in/passkeys) - Android implementation
- [iOS Passkeys](https://developer.apple.com/documentation/authenticationservices/public-private_key_authentication) - iOS implementation  
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) - Cross-platform UI framework
- [WebAuthn Guide](https://webauthn.guide/) - Understanding passkeys

---

## 📄 License

MIT License - Feel free to use this for your own demos and learning!
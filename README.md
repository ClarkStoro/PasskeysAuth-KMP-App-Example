# PasskeysAuth KMP App Example

This project is a **Compose Multiplatform** demo showcasing passkeys authentication. It includes a simple MVI architecture with an authentication flow (registration and login) and a basic TODO screen that interacts with a secured API requiring an auth token.

## Features

- **Compose Multiplatform:** Shared UI logic for Android and iOS.
- **Passkeys Authentication:** Seamless and secure login using passkeys.
- **MVI Architecture:** Clear separation of state, intent, and logic.
- **Token-based API calls:** The TODO screen fetches data from a protected endpoint requiring a valid auth token.

## Passkeys Integration

- `PasskeysCredentialManager`: An interface defining the logic for passkey handling.
- `PasskeysCredentialManagerImpl`: Platform-specific implementations for **Android** and **iOS** using Kotlin.

## Important

This app relies on a backend server for passkeys authentication and API requests. To see it in action, you'll need to run your own backend. You can find a sample backend implementation here: [PasskeysAuth-BE-Example](https://github.com/ClarkStoro/PasskeysAuth-BE-Example)

## License

This project is licensed under the MIT License.
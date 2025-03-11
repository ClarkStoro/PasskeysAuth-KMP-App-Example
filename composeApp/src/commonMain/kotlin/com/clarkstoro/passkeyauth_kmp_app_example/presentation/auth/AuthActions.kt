package com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth

sealed interface AuthActions {
    data class RegistrationPressed(val username: String): AuthActions
    data class LoginPressed(val username: String): AuthActions
}
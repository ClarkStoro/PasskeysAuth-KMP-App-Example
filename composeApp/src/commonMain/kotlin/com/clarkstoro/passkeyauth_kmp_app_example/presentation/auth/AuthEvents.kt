package com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth

sealed interface AuthEvents {
    data object NavigateToHome: AuthEvents
}
package com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth

enum class LoadingType {
    REGISTRATION,
    LOGIN
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val loadingType: LoadingType? = null,
    val isRegistrationSuccess: Boolean? = null,
    val isLoginSuccess: Boolean? = null
)
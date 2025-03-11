package com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth

data class AuthUiState(
    val isLoading: Boolean = false,
    val isRegistrationSuccess: Boolean? = null,
    val isLoginSuccess: Boolean? = null
)
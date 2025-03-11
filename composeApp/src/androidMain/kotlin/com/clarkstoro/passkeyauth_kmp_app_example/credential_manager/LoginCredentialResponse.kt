package com.clarkstoro.passkeyauth_kmp_app_example.credential_manager

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredentialResponse(
    val id: String,
    val response: LoginAuthResponse
)

@Serializable
data class LoginAuthResponse(
    val clientDataJSON: String,
    val authenticatorData: String,
    val signature: String,
    val userHandle: String
)
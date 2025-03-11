package com.clarkstoro.passkeyauth_kmp_app_example.credential_manager

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationCredentialResponse(
    val response: RegistrationAuthResponse
)

@Serializable
data class RegistrationAuthResponse(
    val clientDataJSON: String,
    val attestationObject: String
)
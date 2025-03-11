package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login

import kotlinx.serialization.Serializable

@Serializable
data class StartLoginResponseDTO(
    val challenge: String,
    val allowCredentials: List<AllowCredentialDTO>,
    val timeout: Long,
    val userVerification: String,
    val rpId: String
)
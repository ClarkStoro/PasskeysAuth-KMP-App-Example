package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login

import kotlinx.serialization.Serializable

@Serializable
data class CompleteLoginResponseDTO(
    val token: String,
    val message: String
)
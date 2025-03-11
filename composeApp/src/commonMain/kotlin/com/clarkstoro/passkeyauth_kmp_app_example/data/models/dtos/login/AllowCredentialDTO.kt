package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login

import kotlinx.serialization.Serializable

@Serializable
data class AllowCredentialDTO(
    val type: String = "public-key",
    val id: String
)
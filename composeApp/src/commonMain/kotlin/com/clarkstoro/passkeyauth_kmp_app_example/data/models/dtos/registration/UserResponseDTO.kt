package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDTO(
    val id: String,
    val name: String
)

package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration

import kotlinx.serialization.Serializable

@Serializable
data class CompleteRegistrationRequestDTO(
    val clientDataJSON: String,
    val attestationObject: String
)
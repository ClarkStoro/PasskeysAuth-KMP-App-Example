package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration

import kotlinx.serialization.Serializable

@Serializable
data class StartRegistrationResponseDTO(
    val rp: Rp,
    val user: UserRegistration,
    val challenge: String,
    val pubKeyCredParams: List<PublicKeyCredentialParams>,
    val authenticatorSelection: AuthenticatorSelection,
    val timeout: Long,
    val attestation: String,
)

@Serializable
data class Rp(
    val name: String,
    val id: String
)

@Serializable
data class UserRegistration(
    val id: String,
    val name: String,
    val displayName: String
)

@Serializable
data class PublicKeyCredentialParams(
    val type: String,
    val alg: Int
)

@Serializable
data class AuthenticatorSelection(
    val userVerification: String = "preferred",
    val residentKey: String = "required",
    val authenticatorAttachment: String = "platform",
    val requireResidentKey: Boolean = true
)
package com.clarkstoro.passkeyauth_kmp_app_example.credential_manager

import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.StartLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.StartRegistrationResponseDTO

interface PasskeysCredentialManager {
    suspend fun registerPasskey(
        data: StartRegistrationResponseDTO
    ): CompleteRegistrationRequestDTO

    suspend fun loginPasskey(
        data: StartLoginResponseDTO
    ): CompleteLoginRequestDTO
}
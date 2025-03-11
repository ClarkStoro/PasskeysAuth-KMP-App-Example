package com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories

import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.StartLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.StartRegistrationResponseDTO

interface AuthRepository {

    suspend fun startPasskeyRegistration(username: String): StartRegistrationResponseDTO
    suspend fun completePasskeyRegistration(username: String, request: CompleteRegistrationRequestDTO): String

    suspend fun startPasskeyLogin(username: String): StartLoginResponseDTO
    suspend fun completePasskeyLogin(username: String, request: CompleteLoginRequestDTO): String
}
package com.clarkstoro.passkeyauth_kmp_app_example.data.repositories

import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.StartLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.StartRegistrationResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.network.AuthApi
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun startPasskeyRegistration(username: String): StartRegistrationResponseDTO {
        return authApi.startRegistration(username)
    }

    override suspend fun completePasskeyRegistration(username: String, request: CompleteRegistrationRequestDTO): String {
        return authApi.completeRegistration(username, request)
    }

    override suspend fun startPasskeyLogin(username: String): StartLoginResponseDTO {
        return authApi.startLogin(username)
    }

    override suspend fun completePasskeyLogin(username: String, request: CompleteLoginRequestDTO): String {
        return authApi.completeLogin(username, request).message
    }
}
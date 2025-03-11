package com.clarkstoro.passkeyauth_kmp_app_example.data.network

import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.StartLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.StartRegistrationResponseDTO
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val networkClient: NetworkClient) {

    companion object {
        private const val BASE_ROUTE_AUTH = "auth"

        private const val START_REGISTRATION_ENDPOINT = "$BASE_ROUTE_AUTH/register/start"
        private const val COMPLETE_REGISTRATION_ENDPOINT = "$BASE_ROUTE_AUTH/register/complete"

        private const val START_LOGIN_ENDPOINT = "$BASE_ROUTE_AUTH/login/start"
        private const val COMPLETE_LOGIN_ENDPOINT = "$BASE_ROUTE_AUTH/login/complete"

        private const val USERNAME_QUERY_PARAM = "username"
    }

    suspend fun startRegistration(username: String): StartRegistrationResponseDTO {
        return networkClient.httpClient.post(START_REGISTRATION_ENDPOINT) {
            url {
                parameter(USERNAME_QUERY_PARAM, username)
            }
        }.body<StartRegistrationResponseDTO>()
    }

    suspend fun completeRegistration(username: String, request: CompleteRegistrationRequestDTO): String {
        return networkClient.httpClient.post(COMPLETE_REGISTRATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            url {
                parameter(USERNAME_QUERY_PARAM, username)
            }
            setBody(request)
        }.body<CompleteRegistrationResponseDTO>().message
    }

    suspend fun startLogin(username: String): StartLoginResponseDTO {
        return networkClient.httpClient.post(START_LOGIN_ENDPOINT) {
            url {
                parameter(USERNAME_QUERY_PARAM, username)
            }
        }.body<StartLoginResponseDTO>()
    }

    suspend fun completeLogin(username: String, request: CompleteLoginRequestDTO): CompleteLoginResponseDTO {
        val loginResponse = networkClient.httpClient.post(COMPLETE_LOGIN_ENDPOINT) {
            contentType(ContentType.Application.Json)
            url {
                parameter(USERNAME_QUERY_PARAM, username)
            }
            setBody(request)
        }.body<CompleteLoginResponseDTO>()
        networkClient.saveToken(loginResponse.token)
        return loginResponse
    }
}
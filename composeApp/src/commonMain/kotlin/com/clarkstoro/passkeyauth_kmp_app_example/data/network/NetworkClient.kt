package com.clarkstoro.passkeyauth_kmp_app_example.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class NetworkClient {

    companion object {
        private const val BASE_URL = "https://passkeysauth-be-example.onrender.com/"
    }

    private var jwtToken = ""

    val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }

        defaultRequest {
            url (BASE_URL)
            header(HttpHeaders.Authorization, "Bearer $jwtToken")
        }
    }

    fun saveToken(token: String) {
        this.jwtToken = token
    }

}
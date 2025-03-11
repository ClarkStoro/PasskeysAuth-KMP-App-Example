package com.clarkstoro.passkeyauth_kmp_app_example.credential_manager

import android.annotation.SuppressLint
import android.content.Context
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CreatePublicKeyCredentialResponse
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PublicKeyCredential
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.StartLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.StartRegistrationResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.AuthMethodNotSupported
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PasskeysCredentialManagerImpl (
    private val context: Context
): PasskeysCredentialManager {

    private val jsonParser = Json { ignoreUnknownKeys = true }

    @SuppressLint("PublicKeyCredential")
    override suspend fun registerPasskey(
        data: StartRegistrationResponseDTO
    ): CompleteRegistrationRequestDTO {
        val credentialManager = CredentialManager.create(context = context)
        val createPublicKeyCredentialRequest = CreatePublicKeyCredentialRequest(
            requestJson = Json.encodeToString(data),
            preferImmediatelyAvailableCredentials = true,
        )

        val result = credentialManager.createCredential(
            context = context,
            request = createPublicKeyCredentialRequest,
        ) as CreatePublicKeyCredentialResponse

        val credentialResponse = jsonParser.decodeFromString<RegistrationCredentialResponse>(result.registrationResponseJson)
        return CompleteRegistrationRequestDTO(
            clientDataJSON = credentialResponse.response.clientDataJSON,
            attestationObject = credentialResponse.response.attestationObject
        )
    }

    override suspend fun loginPasskey(
        data: StartLoginResponseDTO
    ): CompleteLoginRequestDTO {
        val credentialManager = CredentialManager.create(context = context)
        val getPasswordOption = GetPasswordOption()
        val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
            requestJson = Json.encodeToString(data)
        )
        val getCredRequest = GetCredentialRequest(
            listOf(getPasswordOption, getPublicKeyCredentialOption)
        )

        val result = credentialManager.getCredential(
            context = context,
            request = getCredRequest
        )

        return when (val credential = result.credential) {
            is PublicKeyCredential -> {
                val responseJson = credential.authenticationResponseJson
                val credentialResponse = jsonParser.decodeFromString<LoginCredentialResponse>(responseJson)
                CompleteLoginRequestDTO(
                    credentialId = credentialResponse.id,
                    clientDataJSON = credentialResponse.response.clientDataJSON,
                    authenticatorData = credentialResponse.response.authenticatorData,
                    signature = credentialResponse.response.signature,
                    userHandle = credentialResponse.response.userHandle
                )
            }
            else -> {
                throw AuthMethodNotSupported()
            }
        }
    }
}
package com.clarkstoro.passkeyauth_kmp_app_example.credential_manager

import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.CompleteLoginRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.login.StartLoginResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.CompleteRegistrationRequestDTO
import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.registration.StartRegistrationResponseDTO
import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.AuthMethodNotSupported
import com.clarkstoro.passkeyauth_kmp_app_example.utils.toByteArray
import com.clarkstoro.passkeyauth_kmp_app_example.utils.toNSData
import io.ktor.utils.io.core.toByteArray
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASAuthorizationPlatformPublicKeyCredentialAssertion
import platform.AuthenticationServices.ASAuthorizationPlatformPublicKeyCredentialProvider
import platform.AuthenticationServices.ASAuthorizationPlatformPublicKeyCredentialRegistration
import platform.AuthenticationServices.ASAuthorizationPublicKeyCredentialAttestationKindNone
import platform.AuthenticationServices.ASPresentationAnchor
import platform.Foundation.NSError
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.io.encoding.Base64
import kotlin.io.encoding.Base64.PaddingOption
import kotlin.io.encoding.ExperimentalEncodingApi


class PasskeysCredentialManagerImpl : PasskeysCredentialManager {

    private val credentialManagerBridge = CredentialManagerBridge()

    override suspend fun registerPasskey(
        data: StartRegistrationResponseDTO
    ): CompleteRegistrationRequestDTO {
        return credentialManagerBridge.registerPasskey(data)
    }

    override suspend fun loginPasskey(
        data: StartLoginResponseDTO
    ): CompleteLoginRequestDTO {
        return credentialManagerBridge.loginPasskey(data)
    }

    inner class CredentialManagerBridge : NSObject(), ASAuthorizationControllerDelegateProtocol,
        ASAuthorizationControllerPresentationContextProvidingProtocol {

        private var currentRegistrationCompletionHandler: ((Result<ASAuthorizationPlatformPublicKeyCredentialRegistration>) -> Unit)? =
            null
        private var currentLoginCompletionHandler: ((Result<ASAuthorizationPlatformPublicKeyCredentialAssertion>) -> Unit)? =
            null

        @OptIn(ExperimentalEncodingApi::class)
        suspend fun registerPasskey(
            data: StartRegistrationResponseDTO
        ): CompleteRegistrationRequestDTO {
            return suspendCoroutine { continuation ->

                val rpIdentifier = data.rp.id
                val publicKeyProvider =
                    ASAuthorizationPlatformPublicKeyCredentialProvider(rpIdentifier)

                // Use the user ID and name from the parsed data
                val userID = data.user.id.toByteArray().toNSData()

                val createRequest =
                    publicKeyProvider.createCredentialRegistrationRequestWithChallenge(
                        Base64.UrlSafe.decode(data.challenge).toNSData(),
                        name = data.user.name,
                        userID = userID
                    )

                createRequest.attestationPreference =
                    ASAuthorizationPublicKeyCredentialAttestationKindNone

                val authController = ASAuthorizationController(listOf(createRequest))
                authController.delegate = this
                authController.presentationContextProvider = this

                currentRegistrationCompletionHandler = { result ->

                    result.fold(
                        onSuccess = { credResponse ->

                            val clientDataJSON =
                                Base64.UrlSafe.encode(credResponse.rawClientDataJSON.toByteArray())

                            val attestationObject =
                                credResponse.rawAttestationObject?.toByteArray()?.let {
                                    Base64.UrlSafe.encode(it)
                                }.orEmpty()

                            val finishRegistrationRequest = CompleteRegistrationRequestDTO(
                                clientDataJSON = clientDataJSON,
                                attestationObject = attestationObject
                            )
                            continuation.resume(finishRegistrationRequest)
                        },
                        onFailure = {
                            continuation.resumeWithException(it)
                        }
                    )
                }

                authController.performRequests()
            }
        }

        @OptIn(ExperimentalEncodingApi::class)
        suspend fun loginPasskey(
            data: StartLoginResponseDTO
        ): CompleteLoginRequestDTO {
            return suspendCoroutine { continuation ->

                val rpIdentifier = data.rpId
                val publicKeyProvider =
                    ASAuthorizationPlatformPublicKeyCredentialProvider(rpIdentifier)

                // Use the user ID and name from the parsed data
                val challenge = Base64.UrlSafe.decode(data.challenge).toNSData()
                val createRequest =
                    publicKeyProvider.createCredentialAssertionRequestWithChallenge(challenge)

                val authController = ASAuthorizationController(listOf(createRequest))
                authController.delegate = this
                authController.presentationContextProvider = this

                currentLoginCompletionHandler = { result ->
                    result.fold(
                        onSuccess = { credResponse->

                            val clientDataJSON =
                                Base64.UrlSafe.encode(credResponse.rawClientDataJSON.toByteArray())

                            val authenticatorData = credResponse.rawAuthenticatorData?.toByteArray()?.let {
                                Base64.UrlSafe.encode(it)
                            }.orEmpty()

                            val signature = credResponse.signature?.toByteArray()?.let {
                                Base64.UrlSafe.encode(it)
                            }.orEmpty()

                            val credentialId = Base64.UrlSafe.withPadding(PaddingOption.ABSENT).encode(credResponse.credentialID.toByteArray())

                            val userHandle = credResponse.userID?.toByteArray()?.let {
                                Base64.UrlSafe.encode(it)
                            }.orEmpty()

                            val finishLoginRequest = CompleteLoginRequestDTO(
                                credentialId = credentialId,
                                clientDataJSON = clientDataJSON,
                                authenticatorData = authenticatorData,
                                signature = signature,
                                userHandle = userHandle
                            )
                            continuation.resume(finishLoginRequest)
                        },
                        onFailure = {
                            continuation.resumeWithException(it)
                        }
                    )
                }

                authController.performRequests()
            }
        }

        override fun authorizationController(
            controller: ASAuthorizationController,
            didCompleteWithAuthorization: ASAuthorization
        ) {
            when (val credential = didCompleteWithAuthorization.credential) {

                is ASAuthorizationPlatformPublicKeyCredentialRegistration -> {
                    currentRegistrationCompletionHandler?.invoke(Result.success(credential))
                }

                is ASAuthorizationPlatformPublicKeyCredentialAssertion -> {
                    currentLoginCompletionHandler?.invoke(Result.success(credential))
                }

                else -> {
                    currentRegistrationCompletionHandler?.invoke(Result.failure(AuthMethodNotSupported()))
                    currentLoginCompletionHandler?.invoke(Result.failure(AuthMethodNotSupported()))
                }
            }
        }

        override fun authorizationController(
            controller: ASAuthorizationController,
            didCompleteWithError: NSError
        ) {

            currentLoginCompletionHandler?.invoke(Result.failure(Exception()))
            currentRegistrationCompletionHandler?.invoke(Result.failure(Exception()))
        }

        override fun presentationAnchorForAuthorizationController(controller: ASAuthorizationController): ASPresentationAnchor? {
            return UIApplication.sharedApplication.keyWindow!!
        }
    }
}
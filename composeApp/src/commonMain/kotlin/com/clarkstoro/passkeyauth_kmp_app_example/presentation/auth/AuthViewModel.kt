package com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkstoro.passkeyauth_kmp_app_example.credential_manager.PasskeysCredentialManager
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val passkeysCredentialManager: PasskeysCredentialManager
): ViewModel() {

    companion object {
        private const val DIALOG_SHOW_MS = 5000L
    }

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<AuthEvents>()
    val events = _events.receiveAsFlow()

    fun onUser(action: AuthActions) {
        when (action) {
            is AuthActions.RegistrationPressed -> {
                viewModelScope.launch {
                    register(username = action.username)
                }
            }
            is AuthActions.LoginPressed -> {
                viewModelScope.launch {
                    login(username = action.username)
                }
            }
        }
    }

    private suspend fun register(username: String) {
        try {
            _uiState.update { it.copy(isLoading = true, loadingType = LoadingType.REGISTRATION) }
            val result = authRepository.startPasskeyRegistration(username)
            passkeysCredentialManager.registerPasskey(
                data = result
            ).let { passkeyRegistrationResult ->
                authRepository.completePasskeyRegistration(username, passkeyRegistrationResult)
                setRegistrationResult(true)
            }
        } catch (e: Exception) {
            setRegistrationResult(false)
        }
    }

    private suspend fun login(username: String) {
        try {
            _uiState.update { it.copy(isLoading = true, loadingType = LoadingType.LOGIN) }
            val result = authRepository.startPasskeyLogin(username)
            passkeysCredentialManager.loginPasskey(
                data = result,
            ).let { passkeyLoginResult ->
                authRepository.completePasskeyLogin(username, passkeyLoginResult)
                _events.send(AuthEvents.NavigateToHome)
            }
        } catch (e: Exception) {
            setLoginResult(false)
        }
    }

    private suspend fun setRegistrationResult(isSuccess: Boolean) {
        _uiState.update { it.copy(isRegistrationSuccess = isSuccess, isLoading = false, loadingType = null) }
        delay(DIALOG_SHOW_MS)
        _uiState.update { it.copy(isRegistrationSuccess = null) }
    }

    private suspend fun setLoginResult(isSuccess: Boolean) {
        _uiState.update { it.copy(isLoginSuccess = isSuccess, isLoading = false, loadingType = null) }
        delay(DIALOG_SHOW_MS)
        _uiState.update { it.copy(isLoginSuccess = null) }
    }
}
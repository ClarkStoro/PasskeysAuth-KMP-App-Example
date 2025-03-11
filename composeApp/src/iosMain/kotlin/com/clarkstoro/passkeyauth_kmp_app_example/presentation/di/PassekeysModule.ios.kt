package com.clarkstoro.passkeyauth_kmp_app_example.presentation.di

import com.clarkstoro.passkeyauth_kmp_app_example.credential_manager.PasskeysCredentialManager
import com.clarkstoro.passkeyauth_kmp_app_example.credential_manager.PasskeysCredentialManagerImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual val passkeysModule: Module = module {
    single<PasskeysCredentialManager> { PasskeysCredentialManagerImpl() }
}
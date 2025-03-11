package com.clarkstoro.passkeyauth_kmp_app_example.presentation.di

import com.clarkstoro.passkeyauth_kmp_app_example.credential_manager.PasskeysCredentialManager
import com.clarkstoro.passkeyauth_kmp_app_example.credential_manager.PasskeysCredentialManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val passkeysModule = module {
    single<PasskeysCredentialManager> { PasskeysCredentialManagerImpl(androidContext()) }
}
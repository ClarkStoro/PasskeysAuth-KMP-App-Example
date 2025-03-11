package com.clarkstoro.passkeyauth_kmp_app_example.di

import com.clarkstoro.passkeyauth_kmp_app_example.data.di.dataModule
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.di.passkeysModule
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.di.presentationModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin


fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            dataModule,
            passkeysModule,
            presentationModule
        )
    }
}
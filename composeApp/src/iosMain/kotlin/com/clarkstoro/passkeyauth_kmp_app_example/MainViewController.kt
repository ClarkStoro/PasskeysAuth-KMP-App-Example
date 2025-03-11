package com.clarkstoro.passkeyauth_kmp_app_example

import androidx.compose.ui.window.ComposeUIViewController
import com.clarkstoro.passkeyauth_kmp_app_example.di.initializeKoin
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.MainContent

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) {
    MainContent()
}
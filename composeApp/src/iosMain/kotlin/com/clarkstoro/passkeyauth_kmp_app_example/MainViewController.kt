package com.clarkstoro.passkeyauth_kmp_app_example

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.window.ComposeUIViewController
import com.clarkstoro.passkeyauth_kmp_app_example.di.initializeKoin
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.MainContent

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        MainContent()
    }
}
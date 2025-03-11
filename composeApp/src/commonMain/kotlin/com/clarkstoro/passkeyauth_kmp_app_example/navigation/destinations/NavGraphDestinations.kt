package com.clarkstoro.passkeyauth_kmp_app_example.navigation.destinations

import androidx.navigation.NavOptions

sealed class NavGraphDestinations

interface NavigationAction {
    val destination: NavGraphDestinations
    val navOptions: NavOptions
        get() = NavOptions.Builder().build()
}
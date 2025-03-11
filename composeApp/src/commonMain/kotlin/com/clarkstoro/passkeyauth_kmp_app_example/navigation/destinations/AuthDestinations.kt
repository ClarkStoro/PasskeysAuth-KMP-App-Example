package com.clarkstoro.passkeyauth_kmp_app_example.navigation.destinations

import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data object Auth : NavGraphDestinations() {

    // NAVIGATION

    fun navigateToHome() = object : NavigationAction {
        override val destination: NavGraphDestinations
            get() = Home
        override val navOptions: NavOptions
            get() = NavOptions.Builder()
                .setPopUpTo<Auth>(true)
                .setLaunchSingleTop(true)
                .build()
    }
}
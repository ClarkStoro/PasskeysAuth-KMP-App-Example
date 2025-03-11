package com.clarkstoro.passkeyauth_kmp_app_example.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.clarkstoro.passkeyauth_kmp_app_example.navigation.destinations.Auth
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth.AuthScreen

fun NavGraphBuilder.authGraphScreens(navController: NavController) {
    composable<Auth> {
        AuthScreen(onNavigateToHome = {
            val navHome = Auth.navigateToHome()
            navController.navigate(navHome.destination, navHome.navOptions)
        })
    }
}
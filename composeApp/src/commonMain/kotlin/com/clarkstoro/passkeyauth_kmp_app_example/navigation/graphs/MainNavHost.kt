package com.clarkstoro.passkeyauth_kmp_app_example.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.clarkstoro.passkeyauth_kmp_app_example.navigation.destinations.Auth

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Auth,
        modifier = modifier
    ) {
        authGraphScreens(navController)
        mainGraphScreens(navController)
    }
}
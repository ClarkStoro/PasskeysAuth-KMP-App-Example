package com.clarkstoro.passkeyauth_kmp_app_example.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.clarkstoro.passkeyauth_kmp_app_example.navigation.graphs.MainNavHost

@Composable
fun MainContent(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Passkeys KMP Example") }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            MainNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

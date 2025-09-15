package com.clarkstoro.passkeyauth_kmp_app_example.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.clarkstoro.passkeyauth_kmp_app_example.navigation.destinations.Auth
import com.clarkstoro.passkeyauth_kmp_app_example.navigation.graphs.MainNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showTopBar = currentRoute != Auth::class.qualifiedName
    
    Scaffold(
        modifier = Modifier,
        topBar = {
            if (showTopBar) {
                TopAppBar(
                    title = { Text("🔐 Passkeys Demo") },
                    colors = TopAppBarDefaults.topAppBarColors()
                )
            }
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

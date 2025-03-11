package com.clarkstoro.passkeyauth_kmp_app_example.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.clarkstoro.passkeyauth_kmp_app_example.navigation.destinations.Home
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.todos.TodosScreen

fun NavGraphBuilder.mainGraphScreens(navController: NavController) {
    composable<Home> { backStackEntry ->
        TodosScreen()
    }
}
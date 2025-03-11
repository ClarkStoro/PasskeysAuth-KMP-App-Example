package com.clarkstoro.passkeyauth_kmp_app_example.presentation.di

import com.clarkstoro.passkeyauth_kmp_app_example.presentation.auth.AuthViewModel
import com.clarkstoro.passkeyauth_kmp_app_example.presentation.todos.TodosViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { TodosViewModel(get()) }
}
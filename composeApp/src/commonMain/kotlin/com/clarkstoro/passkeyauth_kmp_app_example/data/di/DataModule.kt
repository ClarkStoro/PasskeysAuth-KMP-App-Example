package com.clarkstoro.passkeyauth_kmp_app_example.data.di

import com.clarkstoro.passkeyauth_kmp_app_example.data.network.AuthApi
import com.clarkstoro.passkeyauth_kmp_app_example.data.network.NetworkClient
import com.clarkstoro.passkeyauth_kmp_app_example.data.network.TodosApi
import com.clarkstoro.passkeyauth_kmp_app_example.data.repositories.AuthRepositoryImpl
import com.clarkstoro.passkeyauth_kmp_app_example.data.repositories.TodosRepositoryImpl
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.AuthRepository
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.TodosRepository
import org.koin.dsl.module

val dataModule = module {
    single<NetworkClient> { NetworkClient() }
    single<AuthApi> { AuthApi(get()) }
    single<TodosApi> { TodosApi(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TodosRepository> { TodosRepositoryImpl(get()) }
}


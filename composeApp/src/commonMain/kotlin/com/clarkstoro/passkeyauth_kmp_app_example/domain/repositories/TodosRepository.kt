package com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories

import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.Todo

interface TodosRepository {
    suspend fun getTodos(): List<Todo>
}
package com.clarkstoro.passkeyauth_kmp_app_example.presentation.todos

import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.Todo

data class TodosState(
    val isLoading: Boolean = false,
    val todos: List<Todo> = emptyList()
)
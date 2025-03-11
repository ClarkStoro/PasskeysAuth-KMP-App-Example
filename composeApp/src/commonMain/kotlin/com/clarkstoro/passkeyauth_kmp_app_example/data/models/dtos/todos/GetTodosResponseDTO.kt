package com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.todos

import kotlinx.serialization.Serializable

@Serializable
data class GetTodosResponseDTO(
    val username: String? = null,
    val todos: List<TodoDTO>? = null
)

@Serializable
data class TodoDTO(
    val title: String? = null,
    val description: String? = null,
    val done: Boolean? = null
)
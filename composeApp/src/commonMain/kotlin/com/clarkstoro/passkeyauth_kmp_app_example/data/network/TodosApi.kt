package com.clarkstoro.passkeyauth_kmp_app_example.data.network

import com.clarkstoro.passkeyauth_kmp_app_example.data.models.dtos.todos.GetTodosResponseDTO
import io.ktor.client.call.body
import io.ktor.client.request.get

class TodosApi (private val networkClient: NetworkClient) {

    companion object {
        private const val TODOS_ENDPOINT = "/todos"
    }

    suspend fun getTodos(): GetTodosResponseDTO {
        return networkClient.httpClient.get(TODOS_ENDPOINT).body<GetTodosResponseDTO>()
    }
}
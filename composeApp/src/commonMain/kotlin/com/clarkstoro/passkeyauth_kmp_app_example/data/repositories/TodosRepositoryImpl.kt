package com.clarkstoro.passkeyauth_kmp_app_example.data.repositories

import com.clarkstoro.passkeyauth_kmp_app_example.data.network.TodosApi
import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.Todo
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.TodosRepository

class TodosRepositoryImpl(
    private val todosApi: TodosApi
): TodosRepository {

    override suspend fun getTodos(): List<Todo> {
        return todosApi.getTodos().todos?.map {
            Todo(
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                done = it.done ?: false
            )
        } ?: emptyList()
    }
}
package com.clarkstoro.passkeyauth_kmp_app_example.data.repositories

import com.clarkstoro.passkeyauth_kmp_app_example.data.network.TodosApi
import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.Todo
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.TodosRepository

class TodosRepositoryImpl(
    private val todosApi: TodosApi
): TodosRepository {

    override suspend fun getTodos(): List<Todo> {
        // For demo purposes, return fake data to test navigation
        //return getFakeTodos()

        return todosApi.getTodos().todos?.map {
             Todo(
                 title = it.title.orEmpty(),
                 description = it.description.orEmpty(),
                 done = it.done ?: false
             )
         } ?: emptyList()
    }

    private fun getFakeTodos(): List<Todo> {
        return listOf(
            Todo(title = "🔐 Test Passkey Login", description = "Verify authentication works smoothly", done = true),
            Todo(title = "🎤 Prepare Demo Script", description = "Practice the presentation flow", done = true),
            Todo(title = "📱 Test on Multiple Devices", description = "Check iOS and Android compatibility", done = false),
            Todo(title = "✨ Polish UI Components", description = "Final touch-ups for Material 3 design", done = true),
            Todo(title = "🚀 Deploy Backend", description = "Ensure server is running for demo", done = false),
            Todo(title = "📊 Add Demo Analytics", description = "Track user interactions during presentation", done = false)
        )
    }
}
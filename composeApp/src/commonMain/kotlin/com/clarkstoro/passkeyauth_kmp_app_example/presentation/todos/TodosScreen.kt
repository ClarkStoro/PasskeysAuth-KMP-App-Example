package com.clarkstoro.passkeyauth_kmp_app_example.presentation.todos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarkstoro.passkeyauth_kmp_app_example.domain.models.Todo
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TodosScreen(
    viewModel: TodosViewModel = koinViewModel<TodosViewModel>()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        }
        else -> {
            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.todos) {
                    TodoItem(item = it)
                }
            }
        }
    }
}

@Composable
private fun TodoItem(item: Todo) {
    Column (
        modifier = Modifier.fillMaxWidth().padding(all = 16.dp)
    ) {
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
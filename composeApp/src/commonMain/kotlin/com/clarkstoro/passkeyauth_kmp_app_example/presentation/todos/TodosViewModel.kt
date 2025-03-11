package com.clarkstoro.passkeyauth_kmp_app_example.presentation.todos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkstoro.passkeyauth_kmp_app_example.domain.repositories.TodosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodosViewModel(
    private val todosRepository: TodosRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodosState>(TodosState())
    val uiState = _uiState.asStateFlow()

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            val todos = todosRepository.getTodos()
            _uiState.update {
                it.copy(todos = todos)
            }
        }
    }
}
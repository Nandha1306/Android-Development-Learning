package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.network.RetrofitInstance
import com.example.todoapp.network.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos = _todos.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {

        viewModelScope.launch {

            try {
                _todos.value = RetrofitInstance.api.getTodos()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}
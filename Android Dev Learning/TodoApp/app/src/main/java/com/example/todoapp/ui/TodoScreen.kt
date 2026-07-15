package com.example.todoapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel()
) {

    val todos by viewModel.todos.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(todos) { todo ->

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {

                Text(
                    text = todo.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )

            }

        }

    }

}
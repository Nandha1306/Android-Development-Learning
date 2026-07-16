package com.example.repolearning.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.repolearning.BookViewModel

@Composable
fun BookScreen(
    viewModel: BookViewModel
) {
    val books by viewModel.books.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(books) { book ->
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = book.title,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
package com.example.repolearning

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        _books.value = repository.getBooks()
    }
}
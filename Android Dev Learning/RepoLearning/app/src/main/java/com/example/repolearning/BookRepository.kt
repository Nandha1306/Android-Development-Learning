package com.example.repolearning

interface BookRepository {
    fun getBooks(): List<Book>
}
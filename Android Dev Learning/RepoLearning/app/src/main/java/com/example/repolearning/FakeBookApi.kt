package com.example.repolearning

class FakeBookApi {

    fun getBooks(): List<Book> {
        return listOf(
            Book(1, "Clean Code")
        )
    }
}
package com.example.repolearning

class BookRepositoryImpl(
    private val api: FakeBookApi
) : BookRepository {

    override fun getBooks(): List<Book> {
        return api.getBooks()
    }
}
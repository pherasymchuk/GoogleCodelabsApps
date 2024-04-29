package com.example.bookshelf.fakes

import com.example.bookshelf.data.BooksResponse
import com.example.bookshelf.data.network.GoogleBooksApiService

class FakeBooksApiService : GoogleBooksApiService {
    override suspend fun getBooks(query: String): BooksResponse {
        return FakeBooksDatasource.getBooks()
    }
}

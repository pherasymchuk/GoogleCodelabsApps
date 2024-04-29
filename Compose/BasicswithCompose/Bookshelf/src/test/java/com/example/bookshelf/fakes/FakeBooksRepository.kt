package com.example.bookshelf.fakes

import com.example.bookshelf.data.BooksResponse
import com.example.bookshelf.data.network.GoogleBooksApiService
import com.example.bookshelf.data.repositories.BooksRepository

class FakeBooksRepository(private val apiService: GoogleBooksApiService) : BooksRepository {
    override suspend fun getBooks(query: String): BooksResponse {
        return apiService.getBooks("")
    }
}

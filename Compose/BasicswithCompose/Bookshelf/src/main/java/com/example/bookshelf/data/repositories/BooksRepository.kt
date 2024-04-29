package com.example.bookshelf.data.repositories

import com.example.bookshelf.data.BooksResponse
import com.example.bookshelf.data.network.GoogleBooksApiService

interface BooksRepository {
    suspend fun getBooks(query: String): BooksResponse

    class Default(private val apiService: GoogleBooksApiService) : BooksRepository {
        override suspend fun getBooks(query: String): BooksResponse {
            return apiService.getBooks(query = query)
        }
    }
}

package com.example.bookshelf.data.network

import com.example.bookshelf.data.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("books/v1/volumes")
    suspend fun getBooks(@Query("q") query: String): BooksResponse
}


package com.example.bookshelf.data.di

import com.example.bookshelf.data.network.GoogleBooksApiService
import com.example.bookshelf.data.repositories.BooksRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


interface AppContainer {
    val booksRepository: BooksRepository

    class Default : AppContainer {
        private val json: Json = Json { this.ignoreUnknownKeys = true }
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
                .build()
        }
        private val apiService: GoogleBooksApiService by lazy {
            retrofit.create(GoogleBooksApiService::class.java)
        }
        override val booksRepository: BooksRepository = BooksRepository.Default(apiService)
    }

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/"
    }
}

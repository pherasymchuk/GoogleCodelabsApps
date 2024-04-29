package com.example.bookshelf

import com.example.bookshelf.data.repositories.BooksRepository
import com.example.bookshelf.fakes.FakeBooksApiService
import com.example.bookshelf.fakes.FakeBooksDatasource
import com.example.bookshelf.fakes.FakeBooksRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BooksRepositoryTest {
    private lateinit var repository: BooksRepository

    @BeforeEach
    fun initialize() {
        repository = FakeBooksRepository(FakeBooksApiService())
    }

    @Test
    fun `BooksRepository returns correct Books from api service`() = runTest {
        val expected = FakeBooksDatasource.getBooks()
        val actual = repository.getBooks("")
        advanceUntilIdle()
        Assertions.assertEquals(
            expected,
            actual,
            "The results returned by the repository were inconsistent with those obtained via BooksApiService. "
        )
    }
}

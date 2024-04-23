package com.example.amphibians.data.di

import com.example.amphibians.data.network.AmphibiansApiService
import com.example.amphibians.data.repositories.AmphibiansRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val amphibiansRepository: AmphibiansRepository

    object Default : AppContainer {
        private val kotlinxConverterFactory by lazy {
            Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
        }
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(AmphibiansApiService.BASE_URL)
                .addConverterFactory(kotlinxConverterFactory).build()
        }

        private val amphibiansApi: AmphibiansApiService by lazy {
            retrofit.create(AmphibiansApiService::class.java)
        }

        override val amphibiansRepository: AmphibiansRepository by lazy {
            AmphibiansRepository.Network(amphibiansApi)
        }
    }
}

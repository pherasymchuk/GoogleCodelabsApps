package com.example.marsphotos.di

import com.example.marsphotos.data.MarsPhotosRepository
import com.example.marsphotos.data.NetworkMarsPhotosRepository
import com.example.marsphotos.network.MarsApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository

    class Default : AppContainer {

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

        val marsApiService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }

        companion object {
            private const val BASE_URL =
                "https://android-kotlin-fun-mars-server.appspot.com"
        }

        override val marsPhotosRepository: MarsPhotosRepository = NetworkMarsPhotosRepository(marsApiService)
    }
}

package com.example.marsphotos.data

import com.example.marsphotos.network.MarsApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val marsPhotosRepository: MarsPhotosRepository

    class Default : AppContainer {

        /**
         * Use the Retrofit builder to build a [retrofit] object using a kotlinx.serialization converter
         */
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()

        private val retrofitService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }

        private companion object {
            const val BASE_URL =
                "https://android-kotlin-fun-mars-server.appspot.com"
        }

        override val marsPhotosRepository: MarsPhotosRepository by lazy {
            NetworkMarsPhotosRepository(retrofitService)
        }
    }
}

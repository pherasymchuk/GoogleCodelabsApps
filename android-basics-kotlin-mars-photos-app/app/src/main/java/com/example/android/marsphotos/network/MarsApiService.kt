package com.example.android.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofitBuilder = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {

    @retrofit2.http.GET("photos")
    suspend fun getPhotos(): String

    class Base : MarsApiService {

        override suspend fun getPhotos(): String {
            TODO("Not yet implemented")
        }
    }
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofitBuilder.create(MarsApiService::class.java)
    }
}

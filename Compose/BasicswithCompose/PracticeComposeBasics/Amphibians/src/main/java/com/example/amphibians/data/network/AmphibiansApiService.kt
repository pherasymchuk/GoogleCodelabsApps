package com.example.amphibians.data.network

import retrofit2.http.GET


interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>

    companion object {
        const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
    }
}

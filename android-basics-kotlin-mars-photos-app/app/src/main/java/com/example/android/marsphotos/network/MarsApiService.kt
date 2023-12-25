package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

abstract class Api {
    protected abstract val moshi: Moshi
    protected abstract val retrofit: Retrofit
    abstract val retrofitService: MarsApiService

    object MarsApi : Api() {
        override val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        override val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        override val retrofitService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }
    }

    companion object {
        const val BASE_URL: String = "https://android-kotlin-fun-mars-server.appspot.com"
    }
}

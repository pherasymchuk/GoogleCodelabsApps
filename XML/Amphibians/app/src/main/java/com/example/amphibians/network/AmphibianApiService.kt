/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface AmphibianApiService {
    @GET("android-basics-kotlin-unit-4-pathway-2-project-api.json")
    suspend fun getAmphibians(): List<Amphibian>
}

interface Api {
    val retrofitService: AmphibianApiService

    object Amphibians : Api {
        private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        override val retrofitService: AmphibianApiService by lazy {
            retrofit.create(AmphibianApiService::class.java)
        }
    }

    private companion object {
        const val BASE_URL =
            "https://developer.android.com/courses/pathways/android-basics-kotlin-unit-4-pathway-2/"
    }
}

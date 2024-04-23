package com.example.amphibians.data.repositories

import com.example.amphibians.data.network.Amphibian
import com.example.amphibians.data.network.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>

    class Network(private val api: AmphibiansApiService) : AmphibiansRepository {

        override suspend fun getAmphibians(): List<Amphibian> {
            return api.getAmphibians()
        }
    }
}

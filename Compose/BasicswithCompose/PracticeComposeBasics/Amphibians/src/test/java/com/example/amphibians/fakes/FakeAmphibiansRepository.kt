package com.example.amphibians.fakes

import com.example.amphibians.data.network.Amphibian
import com.example.amphibians.data.repositories.AmphibiansRepository

class FakeAmphibiansRepository : AmphibiansRepository {
    override suspend fun getAmphibians(): List<Amphibian> {
        return FakeAmphibiansApiService().getAmphibians()
    }
}

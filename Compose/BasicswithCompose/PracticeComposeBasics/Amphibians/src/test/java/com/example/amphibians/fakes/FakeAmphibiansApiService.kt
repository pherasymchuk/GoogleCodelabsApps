package com.example.amphibians.fakes

import com.example.amphibians.data.network.Amphibian
import com.example.amphibians.data.network.AmphibiansApiService

class FakeAmphibiansApiService : AmphibiansApiService {
    override suspend fun getAmphibians(): List<Amphibian> {
        return FakeDatasource.fakeAmphibians
    }
}

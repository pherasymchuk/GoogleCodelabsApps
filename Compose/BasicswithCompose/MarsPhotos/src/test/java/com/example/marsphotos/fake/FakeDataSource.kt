package com.example.marsphotos.fake

import com.example.marsphotos.network.MarsPhoto

object FakeDataSource {
    private val map = mapOf("img1" to "url.1", "1mg2" to "url.2")

    val photosList = map.map {
        MarsPhoto(id = it.key, imageSrc = it.value)
    }
}

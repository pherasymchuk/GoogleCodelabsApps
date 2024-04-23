package com.example.amphibians.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Amphibian(
    val name: String,
    val type: String,
    val descriptions: String,
    @SerialName("img_src") val imgSrc: String
)

package com.example.flightsearch.ui.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class UiAirport(
    val id: Int,
    val name: String,
    val iataCode: String,
) : Parcelable {
    class NavType : androidx.navigation.NavType<UiAirport>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): UiAirport {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, UiAirport::class.java) as UiAirport
            } else {
                bundle.getParcelable<UiAirport>(key) as UiAirport
            }
        }

        override fun parseValue(value: String): UiAirport {
            return Json.decodeFromString<UiAirport>(value)
        }

        override fun serializeAsValue(value: UiAirport): String {
            return Json.encodeToString(value)
        }

        override fun put(bundle: Bundle, key: String, value: UiAirport) {
            bundle.putParcelable(key, value)
        }

    }
}

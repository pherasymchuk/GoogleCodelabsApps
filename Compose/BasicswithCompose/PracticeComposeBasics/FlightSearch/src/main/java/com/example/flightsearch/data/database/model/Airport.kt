package com.example.flightsearch.data.database.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "iata_code") val iataCode: String,
    val passengers: Int,
) : Parcelable {

    class NavType : androidx.navigation.NavType<Airport>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Airport {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, Airport::class.java) as Airport
            } else {
                bundle.getParcelable<Airport>(key) as Airport
            }
        }

        override fun parseValue(value: String): Airport {
            return Json.decodeFromString<Airport>(value)
        }

        override fun serializeAsValue(value: Airport): String {
            return Json.encodeToString(value)
        }

        override fun put(bundle: Bundle, key: String, value: Airport) {
            bundle.putParcelable(key, value)
        }

    }
}

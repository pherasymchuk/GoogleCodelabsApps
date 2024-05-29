package com.example.flightsearch.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "flight_search",
    foreignKeys = [
        ForeignKey(entity = Airport::class, parentColumns = ["id"], childColumns = ["departure_airport_id"]),
        ForeignKey(entity = Airport::class, parentColumns = ["id"], childColumns = ["arrival_airport_id"])
    ],
    indices = [Index(value = ["departure_airport_id"]), Index(value = ["arrival_airport_id"])]
)
data class Flight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "departure_airport_id") val departureAirportId: Int,
    @ColumnInfo(name = "arrival_airport_id") val arrivalAirportId: Int,
    @ColumnInfo(name = "is_favorite") val isFavorite: Int = 0,
)

package com.example.flightsearch.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "departure_code") val departureCode: String,
    @ColumnInfo(name = "destination_code") val destinationCode: String,
)

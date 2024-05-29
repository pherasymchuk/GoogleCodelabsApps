package com.example.flightsearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.database.dao.AirportSearchDao
import com.example.flightsearch.data.database.dao.FlightSearchDao
import com.example.flightsearch.data.database.model.Airport
import com.example.flightsearch.data.database.model.Favorite
import com.example.flightsearch.data.database.model.Flight

@Database(entities = [Airport::class, Favorite::class, Flight::class], version = 2)
abstract class FlightSearchDatabase : RoomDatabase() {
    abstract fun airportSearchDao(): AirportSearchDao
    abstract fun flightSearchDao(): FlightSearchDao

    companion object {
        @Volatile
        private var INSTANCE: FlightSearchDatabase? = null

        fun getInstance(context: Context): FlightSearchDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, FlightSearchDatabase::class.java, "flight_search.db")
                    .createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

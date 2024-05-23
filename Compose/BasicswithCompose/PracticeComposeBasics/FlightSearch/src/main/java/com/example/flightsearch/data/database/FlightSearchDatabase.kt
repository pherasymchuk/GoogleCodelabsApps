package com.example.flightsearch.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class, Favorite::class], version = 1)
abstract class FlightSearchDatabase : RoomDatabase() {
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

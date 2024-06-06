package com.example.flightsearch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightsearch.data.database.model.FavoriteFlight
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFlightToFavorites(flight: FavoriteFlight)

    @Query("SELECT * FROM favorite WHERE id = :id")
    suspend fun findFavoriteFlight(id: Int): List<FavoriteFlight>

    @Query("SELECT * FROM favorite")
    fun getAllFavoriteFlights(): Flow<List<FavoriteFlight>>

    @androidx.room.Delete
    fun removeFlightFromFavorites(flight: FavoriteFlight)
}

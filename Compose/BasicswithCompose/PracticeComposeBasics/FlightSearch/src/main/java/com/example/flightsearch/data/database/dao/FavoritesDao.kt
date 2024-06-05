package com.example.flightsearch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightsearch.data.database.model.FavoriteFlight

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFlightToFavorites(flight: FavoriteFlight)

    @Query(
        """
        SELECT * FROM favorite
        WHERE departure_code = :departIata
        AND departure_code = :arrivalIata
    """
    )
    suspend fun findFavoriteFlight(departIata: String, arrivalIata: String): List<FavoriteFlight>
}

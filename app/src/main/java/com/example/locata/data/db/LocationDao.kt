package com.example.locata.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.locata.data.db.entities.Location
import com.example.locata.data.db.entities.Route


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLocation(location: Location):Long

    @Query("SELECT * FROM location")
    fun getlocation() : LiveData<Location>
}
package com.glib.core.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM JsonResponseModel WHERE url = :url")
    fun getLocalResponse(url: String): List<JsonResponseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetworkResponse(response: JsonResponseModel)

    // For future use, in case we want to clear record before specific time period
    @Query("DELETE FROM JsonResponseModel WHERE timeStamp <= :timeStamp")
    suspend fun deleteResponse(timeStamp: Long)
}
package com.jasmeet.wallcraft.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadsEntity


@Dao
interface DownloadsDao {

    @Query("SELECT * FROM downloads")
    fun getAllPhotos(): List<DownloadsEntity>

    @Upsert
    suspend fun insertPhotos(photo: DownloadsEntity)

    @Delete
    suspend fun deletePhoto(photo: DownloadsEntity)
}
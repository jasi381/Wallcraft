package com.jasmeet.wallcraft.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasmeet.wallcraft.model.apiResponse.local.DownloadEntity

@Dao
interface DownloadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(download: DownloadEntity)

    @Query("SELECT * FROM downloads")
    suspend fun getAllDownloads(): List<DownloadEntity>

    @Query("SELECT * FROM downloads WHERE url = :url")
    suspend fun getDownloadById(url: String): DownloadEntity
}
package com.jasmeet.wallcraft.model.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jasmeet.wallcraft.model.apiResponse.local.PhotoEntity

@Dao
interface PhotosDao {

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): PagingSource<Int, PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPhotos(photos: List<PhotoEntity>)
}
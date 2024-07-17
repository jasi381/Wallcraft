package com.jasmeet.wallcraft.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jasmeet.wallcraft.model.apiResponse.local.FavouritesEntity

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getAllPhotos(): List<FavouritesEntity>

    @Upsert
    suspend fun insertPhotos(photo: FavouritesEntity)

    @Delete
    suspend fun deletePhoto(photo: FavouritesEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE id = :id)")
    suspend fun isPhotoFavourite(id: String): Boolean
}
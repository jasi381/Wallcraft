package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.local.FavouritesEntity


interface FavouritesRepo {
    suspend fun insertPhoto(photo: FavouritesEntity)
    suspend fun deletePhoto(photo: FavouritesEntity)
    fun getAllPhotos(): List<FavouritesEntity>
    suspend fun isPhotoFavourite(id: String): Boolean
}

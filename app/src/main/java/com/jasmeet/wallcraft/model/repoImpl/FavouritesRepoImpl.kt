package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.local.FavouritesEntity
import com.jasmeet.wallcraft.model.dao.FavouriteDao
import com.jasmeet.wallcraft.model.repo.FavouritesRepo
import javax.inject.Inject

class FavouritesRepoImpl @Inject constructor(
    private val favouriteDao: FavouriteDao
) : FavouritesRepo {

    override suspend fun insertPhoto(photo: FavouritesEntity) {
        favouriteDao.insertPhotos(photo)
    }

    override suspend fun deletePhoto(photo: FavouritesEntity) {
        favouriteDao.deletePhoto(photo)
    }

    override fun getAllPhotos(): List<FavouritesEntity> {
        return favouriteDao.getAllPhotos()
    }

    override suspend fun isPhotoFavourite(id: String): Boolean {
        return favouriteDao.isPhotoFavourite(id)
    }
}

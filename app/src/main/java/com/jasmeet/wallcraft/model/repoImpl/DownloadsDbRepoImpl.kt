package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.local.DownloadsEntity
import com.jasmeet.wallcraft.model.dao.DownloadsDao
import com.jasmeet.wallcraft.model.repo.DownloadsDbRepo
import javax.inject.Inject

class DownloadsDbRepoImpl @Inject constructor(
    private val downloadsDao: DownloadsDao
) : DownloadsDbRepo {
    override suspend fun insertPhoto(photo: DownloadsEntity) {
        downloadsDao.insertPhotos(photo)
    }

    override suspend fun deletePhoto(photo: DownloadsEntity) {
        downloadsDao.deletePhoto(photo)
    }

    override fun getAllPhotos(): List<DownloadsEntity> {
        return downloadsDao.getAllPhotos()
    }
}
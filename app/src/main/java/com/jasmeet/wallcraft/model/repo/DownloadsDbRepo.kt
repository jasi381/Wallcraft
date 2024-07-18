package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.local.DownloadsEntity


interface DownloadsDbRepo {
    suspend fun insertPhoto(photo: DownloadsEntity)
    suspend fun deletePhoto(photo: DownloadsEntity)
    fun getAllPhotos(): List<DownloadsEntity>
}

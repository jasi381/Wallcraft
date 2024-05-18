package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse.PhotographerPhotosApiResponse

interface PhotographerPhotosRepo {

    suspend fun getPhotos(postedBy: String, orderBy: String): List<PhotographerPhotosApiResponse>
}
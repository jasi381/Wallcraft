package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.photoGrapherPhotosApiResponse.PhotographerPhotosApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.PhotographerPhotosRepo

class PhotographerPhotosRepoImpl(private val apiService: ApiService) : PhotographerPhotosRepo {
    override suspend fun getPhotos(
        postedBy: String,
        orderBy: String
    ): List<PhotographerPhotosApiResponse> {
        return apiService.getPostedByPhotos(username = postedBy, orderBy = orderBy)
    }
}
package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.DetailsRepo

class DetailsRepoImpl(private val apiService: ApiService) : DetailsRepo {
    override suspend fun getWallpaperDetails(id: String): DetailsApiResponse {
        return apiService.getWallpaperDetails(id = id)
    }

}
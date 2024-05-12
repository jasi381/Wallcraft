package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.detailsApiResponse.DetailsApiResponse

interface DetailsRepo {
    suspend fun getWallpaperDetails(id: String): DetailsApiResponse
}
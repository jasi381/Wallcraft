package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.ApiResponse

interface SearchRepo {
    suspend fun getSearchedWallpapers(query: String, page: Int): ApiResponse
}
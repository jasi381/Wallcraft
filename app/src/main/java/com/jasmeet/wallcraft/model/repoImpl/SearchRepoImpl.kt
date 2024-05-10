package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.ApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.SearchRepo

class SearchRepoImpl(private val apiService: ApiService) : SearchRepo {
    override suspend fun getSearchedWallpapers(query: String, page: Int): ApiResponse {
        return apiService.getSearchScreenData(query = query, page = page)
    }
}
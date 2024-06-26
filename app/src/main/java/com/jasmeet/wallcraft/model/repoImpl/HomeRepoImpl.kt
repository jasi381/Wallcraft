package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.HomeRepo

class HomeRepoImpl(private val apiService: ApiService) : HomeRepo {
    override suspend fun getHomeData(page: Int, orderBy: String): List<HomeApiResponse> {
        return apiService.getHomeScreenData(page = page, orderBy = orderBy)
    }
}
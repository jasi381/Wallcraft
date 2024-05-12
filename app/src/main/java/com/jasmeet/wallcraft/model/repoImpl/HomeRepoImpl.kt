package com.jasmeet.wallcraft.model.repoImpl

import androidx.paging.PagingData
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse
import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.Urls
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.HomeRepo
import kotlinx.coroutines.flow.Flow

class HomeRepoImpl(private val apiService: ApiService) : HomeRepo {
    override suspend fun getHomeData(page: Int): List<HomeApiResponse> {
        return apiService.getHomeScreenData(page)
    }
}
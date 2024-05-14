package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.homeApiResponse.HomeApiResponse

interface HomeRepo {
    suspend fun getHomeData(page: Int, orderBy: String): List<HomeApiResponse>
}
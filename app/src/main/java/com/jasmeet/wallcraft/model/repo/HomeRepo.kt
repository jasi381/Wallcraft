package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.ApiResponse

interface HomeRepo {
    suspend fun getHomeData(page: Int): ApiResponse
}
package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse.RandomImageApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.RandomImageRepo

class RandomImageRepoImpl(private val apiService: ApiService) : RandomImageRepo {
    override suspend fun getRandomImage(): RandomImageApiResponse {
        return apiService.getRandomImage()
    }
}
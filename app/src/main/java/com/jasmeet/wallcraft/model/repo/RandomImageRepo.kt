package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.randomImageApiResponse.RandomImageApiResponse

interface RandomImageRepo {
    suspend fun getRandomImage(): RandomImageApiResponse
}
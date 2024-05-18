package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse.PostedByApiResponse

interface PostedByRepo {
    suspend fun getPhotographerDetails(name: String): PostedByApiResponse
}
package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.postedByApiResponse.PostedByApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.PostedByRepo

class PostedByRepoImpl(private val apiService: ApiService) : PostedByRepo {
    override suspend fun getPhotographerDetails(name: String): PostedByApiResponse {
        return apiService.getPostedByDetails(name)
    }
}
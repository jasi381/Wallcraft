package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.CategoryPhotosRepo

class CategoryPhotosRepoImpl(private val apiService: ApiService) : CategoryPhotosRepo {
    override suspend fun getCategoryDetails(
        page: Int,
        orderBy: String
    ): List<CategoryDetailApiResponse> {
        return apiService.getCategoryPhoto(page = page, orderBy = orderBy)
    }

}
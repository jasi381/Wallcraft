package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailsApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.CategoryDetailsRepo

class CategoryDetailsRepoImpl(private val apiService: ApiService) : CategoryDetailsRepo {
    override suspend fun getCategoryDetails(query: String, page: Int): CategoryDetailsApiResponse {
        return apiService.getCategoryDetails(query = query, page = page)
    }
}
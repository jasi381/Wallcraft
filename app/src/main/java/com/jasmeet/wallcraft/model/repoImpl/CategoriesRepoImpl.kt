package com.jasmeet.wallcraft.model.repoImpl

import com.jasmeet.wallcraft.model.apiResponse.remote.categoriesApiResponse.CategoriesApiResponse
import com.jasmeet.wallcraft.model.apiService.ApiService
import com.jasmeet.wallcraft.model.repo.CategoriesRepo

class CategoriesRepoImpl(private val apiService: ApiService) : CategoriesRepo {
    override suspend fun getCategories(): List<CategoriesApiResponse> {
        return apiService.getCategories()
    }
}
package com.jasmeet.wallcraft.model.repo

import com.jasmeet.wallcraft.model.apiResponse.remote.categoryDetailsApiResponse.CategoryDetailsApiResponse

interface CategoryDetailsRepo {
    suspend fun getCategoryDetails(query: String): CategoryDetailsApiResponse
}